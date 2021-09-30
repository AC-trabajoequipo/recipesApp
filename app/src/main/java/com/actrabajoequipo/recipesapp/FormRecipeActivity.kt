package com.actrabajoequipo.recipesapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.databinding.ActivityFormRecipeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_form_recipe.*


class FormRecipeActivity : AppCompatActivity() {

    private var photoUrl: String? = null
    private val PATH_REALTIME_DATABASE = "recipes"

    private lateinit var binding: ActivityFormRecipeBinding
    private lateinit var viewModel: FormRecipeViewModel

    //firebase RealTimeDataBase
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    //
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.data.let { photoSelectedUri ->
                    binding.photoRecipe.setImageURI(photoSelectedUri)
                    if (photoSelectedUri != null) {
                        viewModel.uploadImage(photoSelectedUri)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_recipe)
        viewModel = ViewModelProvider(this).get(FormRecipeViewModel::class.java)

        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().reference.child(PATH_REALTIME_DATABASE)

        binding.btnAddImage.setOnClickListener { openGallery() }

        viewModel.formState.observe(this, Observer {
            when (it) {
                is FormRecipeViewModel.ValidatedFields.FormValidated -> {
                    with(binding) {
                        //viewModel.uploadRecipe
                        viewModel.saveRecipe(
                            user = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                            photoUrl = photoUrl!!,
                            titleRecipe = etTitleRecipe.text.toString().trim(),
                            ingredients = arrayListOf(
                                etIngredient1.text.toString().trim(),
                                etIngredient2.text.toString().trim(),
                                etIngredient3.text.toString().trim(),
                                etIngredient4.text.toString().trim(),
                                etIngredient5.text.toString().trim()
                            ), stepRecipe = etStepForRecipe.text.toString().trim()
                        )
                    }
                }
                is FormRecipeViewModel.ValidatedFields.EmptyFieldsError -> Toast.makeText(
                    this,
                    R.string.fields_required_empty,
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
        })
        viewModel.recipeState.observe(this, {
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

        })
        viewModel.progressUploadImage.observe(this, { progress ->
            binding.pbUploadImage.progress = progress.toInt()
            binding.tvProgressPercent.text = "$progress%"
        })

        viewModel.imageUpload.observe(this, {
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
        })

        binding.lifecycleOwner = this
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResult.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_add_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_post -> {
                viewModel.validatedFields(ingredients = arrayListOf(
                        etTitleRecipe.text.toString().trim(),
                        etIngredient1.text.toString().trim(),
                        etIngredient2.text.toString().trim(),
                        etIngredient3.text.toString().trim(),
                        etIngredient4.text.toString().trim(),
                        etIngredient5.text.toString().trim()
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}