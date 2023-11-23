package com.actrabajoequipo.recipesapp.ui.formrecipe

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityFormRecipeBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_form_recipe.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.lang.String
import java.util.*

class FormRecipeActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 47

        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private lateinit var binding: ActivityFormRecipeBinding
    private val viewModel by lazy { getViewModel { component.formRecipeViewModel } }
    private lateinit var component: FormRecipeComponent

    private lateinit var currentPhotoPath: String
    private lateinit var photoUri: Uri

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.data.let { photoSelectedUri ->
                    if (photoSelectedUri != null) {
                        binding.photoRecipe.setImageURI(photoSelectedUri)
                        viewModel.uploadImage(photoSelectedUri)
                        return@registerForActivityResult
                    }
                }
                binding.photoRecipe.setImageURI(photoUri)
                viewModel.uploadImage(photoUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        component = app.component.plus(FormRecipeModule())
        super.onCreate(savedInstanceState)

        binding = ActivityFormRecipeBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)
            setSupportActionBar(detailToolbar)
            detailToolbar.setNavigationOnClickListener { onBackClicked() }
            btnAddImage.setOnClickListener { getNewImage() }
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = (getString(R.string.form_add_recipe))

        //permite que al cargar la vista el teclado no tape el formulario.
        //además hace que el teclado no se abrá automaticamente al cargar la activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        viewModel.formState.observe(this) {
            when (it) {
                is FormRecipeViewModel.ValidatedFields.FormValidated -> {
                    with(binding) {
                        //viewModel.uploadRecipe
                        viewModel.saveRecipe(
                            idUser = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                            titleRecipe = etTitleRecipe.text.toString().trim(),
                            descriptionRecipe = etDescription.text.toString().trim(),
                            stepRecipe = etStepForRecipe.text.toString().trim()
                        )
                    }
                }

                is FormRecipeViewModel.ValidatedFields.EmptyTitleRecipeError -> Toast.makeText(
                    this,
                    R.string.formRecipe_title_required,
                    Toast.LENGTH_LONG
                ).show()

                is FormRecipeViewModel.ValidatedFields.EmptyStepsRecipeError -> Toast.makeText(
                    this,
                    R.string.formRecipe_step_required,
                    Toast.LENGTH_LONG
                ).show()

                is FormRecipeViewModel.ValidatedFields.EmptyIngredientsError -> Toast.makeText(
                    this,
                    R.string.formRecipe_ingredients_required,
                    Toast.LENGTH_LONG
                ).show()

                is FormRecipeViewModel.ValidatedFields.EmptyPhotoFieldError -> Toast.makeText(
                    this,
                    R.string.image_not_selected,
                    Toast.LENGTH_LONG
                ).show()
                is FormRecipeViewModel.ValidatedFields.EmptyIdFieldError -> Toast.makeText(
                    this,
                    R.string.connection_firebase_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.recipeState.observe(this) {
            when (it) {
                is FormRecipeViewModel.SaveRecipe.Success -> {
                    Toast.makeText(
                        this,
                        "Receta subida",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is FormRecipeViewModel.SaveRecipe.Error ->
                    Toast.makeText(
                        this,
                        "Fallo al subir la receta",
                        Toast.LENGTH_SHORT
                    ).show()
            }

        }
        
        viewModel.progressUploadImage.observe(this) { progress ->
            binding.pbUploadImage.progress = progress
            val textProgress = String.format(Locale.getDefault(), "%d%%",  progress)
            binding.tvProgressPercent.text = textProgress
        }
        
        viewModel.imageUpload.observe(this) {
            when (it) {
                is FormRecipeViewModel.ImageUpload.Success -> {

                    binding.pbUploadImage.visibility = View.INVISIBLE
                    binding.tvProgressPercent.visibility = View.INVISIBLE

                    Toast.makeText(
                        this,
                        R.string.upload_image_complete,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is FormRecipeViewModel.ImageUpload.InProgress -> {
                    binding.pbUploadImage.visibility = View.VISIBLE
                    binding.tvProgressPercent.visibility = View.VISIBLE

                }
                is FormRecipeViewModel.ImageUpload.Error -> {
                    binding.pbUploadImage.visibility = View.INVISIBLE
                    binding.tvProgressPercent.visibility = View.INVISIBLE
                    Toast.makeText(
                        this,
                        R.string.upload_image_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onBackClicked() {
        finish()
        overridePendingTransition(0, R.anim.slide_out);
    }

    private fun getNewImage() {
        if(checkAllPermissionsGranted(REQUIRED_PERMISSIONS)){
            launchChooser()
            return
        }
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
    }

    private fun launchChooser() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        // intent para coger foto de la camara
        val cameraIntent = getTakePictureIntent()

        // Construimos el chooser que vamos a lanzar
        val pickTitle = getString(R.string.choose_picture_method)
        val chooserIntent: Intent = Intent.createChooser(pickIntent, pickTitle)
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent)
        )

        // Lanzamos el resultLauncher
        resultLauncher.launch(chooserIntent)
    }

    private fun getTakePictureIntent(): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        // Creamos fichero para guardar la imagen de la camara
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    // Definimos content provider en el manifest y en el .kt
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "${packageName}.provider",
                            it
                        )
                        photoUri = photoURI
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    }
                }
            }
        }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("'camera_'ddMMyyyy_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun checkAllPermissionsGranted(permissions: Array<String>): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkAllPermissionsGranted(permissions))
            launchChooser()
}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_add_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_post -> {
                viewModel.validatedFields(
                    titleRecipe = binding.etTitleRecipe.text.toString().trim(),
                    stepRecipe = binding.etStepForRecipe.text.toString().trim(),
                    ingredients = arrayListOf(
                        binding.etIngredient1.text.toString().trim(),
                        binding.etIngredient2.text.toString().trim(),
                        binding.etIngredient3.text.toString().trim(),
                        binding.etIngredient4.text.toString().trim(),
                        binding.etIngredient5.text.toString().trim()
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}