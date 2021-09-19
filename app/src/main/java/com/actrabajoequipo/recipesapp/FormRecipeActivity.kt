package com.actrabajoequipo.recipesapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.actrabajoequipo.recipesapp.databinding.ActivityFormRecipeBinding
import com.google.android.material.snackbar.Snackbar

class FormRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormRecipeBinding
    private var recipe: Recipe? = null

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_recipe)
        binding.lifecycleOwner = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_add_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_save -> {
                //TODO if(recipe != null)
                if(validateFields(binding.etStepForRecipe, binding.etIngredient5, binding.etIngredient4,
                        binding.etIngredient3, binding.etIngredient2, binding.etIngredient1, binding.etTitleRecipe
                    )){
                        with(binding){
                            Recipe(titleRecipe = etTitleRecipe.text.toString().trim(),
                            ingredients = arrayListOf(etIngredient1.text.toString().trim(), etIngredient2.text.toString().trim(),
                            etIngredient3.text.toString().trim(), etIngredient4.text.toString().trim(),
                            etIngredient5.text.toString().trim()), stepRecipe = etStepForRecipe.text.toString().trim())
                        }

                    //Repository.addRecipe
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateFields(vararg editText: EditText): Boolean{
        var isValid = true

        for(et in editText){
            if(et.text.toString().trim().isEmpty()){
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
