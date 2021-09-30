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
    private val PATH_IMAGES = "images"
    private val PATH_REALTIME_DATABASE = "recipes"
    private var recipe: Recipe? = null
    private var photoSelectedUri: Uri? = null
    private var photoUrl: String? = null
    private var id: String? = null
    private lateinit var binding: ActivityFormRecipeBinding
    private lateinit var viewModel: FormRecipeViewModel

    //firebase RealTimeDataBase
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    //
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                photoSelectedUri = it.data?.data

                binding.photoRecipe.setImageURI(photoSelectedUri)
                binding.pbUploadImage.visibility = View.VISIBLE
                binding.tvProgressPercent.visibility = View.VISIBLE
                id = databaseReference.push().key
                val storageReference = FirebaseAuth.getInstance().currentUser?.let {
                    id?.let { idGenerated ->
                        storageReference.child(PATH_IMAGES).child(
                            it.uid
                        ).child(idGenerated)
                    }
                }
                if (photoSelectedUri != null) {
                    storageReference?.putFile(photoSelectedUri!!)
                        ?.addOnProgressListener {
                            val progress =
                                (100 * it.bytesTransferred / it.totalByteCount).toDouble()
                            binding.pbUploadImage.progress = progress.toInt()
                            binding.tvProgressPercent.text = "$progress%"
                        }
                        ?.addOnCompleteListener {
                            binding.pbUploadImage.visibility = View.INVISIBLE
                            binding.tvProgressPercent.visibility = View.INVISIBLE
                        }
                        ?.addOnSuccessListener {
                            // código para una vez que la foto se ha subido exitosa al store
                            //podamos coger la url y ponerla en nuestra database
                            Snackbar.make(binding.root, "imagen subida", Snackbar.LENGTH_SHORT)
                                .show()
                            it.storage.downloadUrl.addOnSuccessListener { photoUrl = it.toString() }
                        }
                        ?.addOnFailureListener {
                            Snackbar.make(
                                binding.root,
                                "No se pudo subir, inténtelo de nuevo",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormRecipeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(FormRecipeViewModel::class.java)

        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().reference.child(PATH_REALTIME_DATABASE)

        with(binding) {
            setContentView(root)
            btnAddImage.setOnClickListener { openGallery() }
        }
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
                viewModel.validatedFields(
                    photoUrl = photoUrl, id = id, ingredients = arrayListOf(
                        etIngredient1.text.toString().trim(),
                        etIngredient2.text.toString().trim(),
                        etIngredient3.text.toString().trim(),
                        etIngredient4.text.toString().trim(),
                        etIngredient5.text.toString().trim()
                    )
                )
                viewModel.formState.observe(this, Observer {
                    when (it) {
                        is FormRecipeViewModel.ValidatedFields.FormValidated -> {
                            with(binding) {
                                //viewModel.uploadRecipe
                                viewModel.saveImage(
                                    user = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                                    id = id!!, photoUrl = photoUrl!!,
                                    titleRecipe = etTitleRecipe.text.toString().trim(),
                                    ingredients = arrayListOf(
                                        etIngredient1.text.toString().trim(),
                                        etIngredient2.text.toString().trim(),
                                        etIngredient3.text.toString().trim(),
                                        etIngredient4.text.toString().trim(),
                                        etIngredient5.text.toString().trim()
                                    ), stepRecipe = etStepForRecipe.text.toString().trim()
                                )
                                viewModel.imageState.observe(this@FormRecipeActivity, {
                                    when (it) {
                                        is FormRecipeViewModel.SaveImage.Success -> {
                                            Toast.makeText(
                                                this@FormRecipeActivity,
                                                "Receta subida",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            finish()
                                        }
                                        is FormRecipeViewModel.SaveImage.Error ->
                                            Toast.makeText(
                                                this@FormRecipeActivity,
                                                "Fallo al subir la receta",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                    }

                                })
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
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}