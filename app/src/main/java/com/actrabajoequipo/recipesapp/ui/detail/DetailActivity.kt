package com.actrabajoequipo.recipesapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityDetailBinding
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.model.database.Recipe
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.actrabajoequipo.recipesapp.ui.loadUrl

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        val args: DetailActivityArgs by navArgs()

        detailViewModel = getViewModel {
            DetailViewModel(args.recipeId, RecipesRepository(app))
        }

        with(binding) {
            setContentView(root)
            setSupportActionBar(detailToolbar)
            detailToolbar.setNavigationOnClickListener { onBackClicked() }
            favorite.setOnClickListener { detailViewModel.onFavoriteClicked() }
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel.findRecipe()

        detailViewModel.recipe.observe(this, Observer(this::updateUI))
    }

    private fun onBackClicked() {
        finish()
        overridePendingTransition(0, R.anim.slide_out);
    }

    private fun updateUI(recipe: Recipe) {

        with(binding) {
            collapsingToolbar.title = recipe.name
            recipe.imgUrl.let { image.loadUrl(it) }
            description.text = recipe.description

            recipe.ingredients.let {
                ingredientsTitle.visibility = View.VISIBLE
                ingredientsRecycler.adapter = IngredientsAdapter(it)
            }
            recipe.preparation.let {
                preparationTitle.visibility = View.VISIBLE
                preparationContent.text = it
            }
            updateLike(recipe.favorite)
        }
    }

    private fun updateLike(favorite: Boolean) {
        val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        binding.favorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, icon))
    }
}