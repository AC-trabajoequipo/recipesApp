package com.actrabajoequipo.recipesapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.actrabajoequipo.recipesapp.databinding.ActivityFormRecipeBinding
import com.actrabajoequipo.recipesapp.ui.userprofile.UserProfileFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FormRecipeActivity : AppCompatActivity() {
    private val RC_GALLERY = 254
    private val PATH_IMAGES = "images"
    private var recipe: Recipe? = null
    private var photoSelectedUri: Uri? = null
    private var photoUrl: String? = null
    private var id: String? = null
    private lateinit var binding: ActivityFormRecipeBinding

    //firebase RealTimeDataBase
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_recipe)

        binding.btnAddImage.setOnClickListener { openGallery() }

        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().reference.child(PATH_IMAGES)

        binding.lifecycleOwner = this
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_GALLERY) {
                photoSelectedUri = data?.data
                binding.photoRecipe.setImageURI(photoSelectedUri)
                binding.pbUploadImage.visibility = View.VISIBLE
                binding.tvProgressPercent.visibility = View.VISIBLE
                id = databaseReference.push().key
                val storageReference = storageReference.child(PATH_IMAGES).child("my_photo")
                if (photoSelectedUri != null) {
                    storageReference.putFile(photoSelectedUri!!)
                        .addOnProgressListener {
                            val progress =
                                (100 * it.bytesTransferred / it.totalByteCount).toDouble()
                            binding.pbUploadImage.progress = progress.toInt()
                            binding.tvProgressPercent.text = "$progress%"
                        }
                        .addOnCompleteListener {
                            binding.pbUploadImage.visibility = View.INVISIBLE
                            binding.tvProgressPercent.visibility = View.INVISIBLE
                        }
                        .addOnSuccessListener {
                            // código para una vez que la foto se ha subido exitosa al store
                            //podamos coger la url y ponerla en nuestra database
                            Snackbar.make(binding.root, "imagen subida", Snackbar.LENGTH_SHORT)
                                .show()
                            it.storage.downloadUrl.addOnSuccessListener { photoUrl = it.toString() }
                        }
                        .addOnFailureListener {
                            Snackbar.make(
                                binding.root,
                                "No se pudo subir, inténtelo de nuevo",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_add_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_post -> {
                //TODO if(recipe != null)
                if (validateFields(
                        binding.etStepForRecipe,
                        binding.etIngredient5,
                        binding.etIngredient4,
                        binding.etIngredient3,
                        binding.etIngredient2,
                        binding.etIngredient1,
                        binding.etTitleRecipe
                    ) && (photoUrl != null) && (id != null)
                ) {
                    with(binding) {
                        saveImage(
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
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveImage(
        id: String,
        photoUrl: String,
        titleRecipe: String,
        ingredients: ArrayList<String>,
        stepRecipe: String
    ) {
        val recipe = Recipe(id, photoUrl, titleRecipe, ingredients, stepRecipe)
        databaseReference.child(id).setValue(recipe)

    }

    private fun validateFields(vararg editText: EditText): Boolean {
        var isValid = true

        for (et in editText) {
            if (et.text.toString().trim().isEmpty()) {
                et.requestFocus()
                isValid = false
            }
        }

        if (!isValid) Snackbar.make(
            binding.root,
            R.string.add_recipe_message_invalid,
            Snackbar.LENGTH_SHORT
        ).show()

        return isValid
    }
}
