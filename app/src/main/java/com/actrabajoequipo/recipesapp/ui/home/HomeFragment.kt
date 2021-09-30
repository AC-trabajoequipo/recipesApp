package com.actrabajoequipo.recipesapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.databinding.FragmentHomeBinding
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.actrabajoequipo.recipesapp.ui.home.HomeViewModel.UIModel
import com.actrabajoequipo.recipesapp.ui.home.adapter.RecipesAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        homeViewModel = getViewModel { HomeViewModel(RecipesRepository(requireContext().app)) }

        homeViewModel.uiModel.observe(this, ::updateUI)
        homeViewModel.navigation.observe(this, { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToDetailActivity(it.id)
                )
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        adapter = RecipesAdapter(homeViewModel::onRecipeClicked)
        binding.recycler.adapter = adapter

        return binding.root
    }

    private fun updateUI(uiModel: UIModel) {

        binding.progress.visibility =
            if (uiModel is UIModel.Loading) View.VISIBLE else View.GONE

        if (uiModel is UIModel.Content) {
            adapter.recipes = uiModel.recipes
        }
    }
}