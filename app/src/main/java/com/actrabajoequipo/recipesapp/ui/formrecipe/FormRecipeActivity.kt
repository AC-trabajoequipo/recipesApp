package com.actrabajoequipo.recipesapp.ui.formrecipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.RecipesApp
import com.actrabajoequipo.recipesapp.databinding.ActivityFormRecipeBinding
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_form_recipe.*


class FormRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormRecipeBinding
    private lateinit var viewModel: FormRecipeViewModel

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

        binding = ActivityFormRecipeBinding.inflate(layoutInflater)

        viewModel = getViewModel { FormRecipeViewModel(RecipesRepository(app)) }

        with(binding) {
            setContentView(root)
            btnAddImage.setOnClickListener { openGallery() }
        }

        viewModel.formState.observe(this, {
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
                    R.string.fields_required_empty,
                    Toast.LENGTH_LONG
                ).show()

                is FormRecipeViewModel.ValidatedFields.EmptyDescriptionRecipeError -> Toast.makeText(
                    this,
                    R.string.fields_required_empty,
                    Toast.LENGTH_LONG
                ).show()

                is FormRecipeViewModel.ValidatedFields.EmptyIngredientsError -> Toast.makeText(
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
    }

    private fun onBackClicked() {
        finish()
        overridePendingTransition(0, R.anim.slide_out);
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
                    titleRecipe = etTitleRecipe.text.toString().trim(),
                    descriptionRecipe = etDescription.text.toString().trim(),
                    ingredients = arrayListOf(
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