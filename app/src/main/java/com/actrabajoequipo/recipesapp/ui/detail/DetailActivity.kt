package com.actrabajoequipo.recipesapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityDetailBinding
import com.actrabajoequipo.recipesapp.model.RecipeDto
import com.actrabajoequipo.recipesapp.ui.home.HomeFragmentDirections
import com.actrabajoequipo.recipesapp.ui.home.HomeViewModel
import com.actrabajoequipo.recipesapp.ui.loadUrl

class DetailActivity : AppCompatActivity() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { onBackClicked() }
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val args: DetailActivityArgs by navArgs()
        updateUI(args.recipe)
    }

    private fun onBackClicked() {
        finish()
        overridePendingTransition(0, R.anim.slide_out);
    }

    private fun updateUI(recipe: RecipeDto) {
        with(binding) {
            toolbar.title = recipe.name
            recipe.imgUrl?.let { image.loadUrl(it) }
            description.text = recipe.description

            recipe.ingredients?.let {
                ingredientsTitle.visibility = View.VISIBLE
                ingredientsRecycler.adapter = IngredientsAdapter(it)
            }
            recipe.preparation?.let {
                preparationTitle.visibility = View.VISIBLE
                preparationContent.text = it
            }
        }
    }
}