package com.actrabajoequipo.recipesapp.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.R
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

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    searchRecipes(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                searchRecipes("")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                setRecipesInitialState()
                return true
            }

        })
    }

    private fun setRecipesInitialState() {
        homeViewModel.refresh()
    }

    private fun searchRecipes(query: String) {
        homeViewModel.doSearch(query)
    }

    private fun updateUI(uiModel: UIModel) {

        binding.progress.visibility =
            if (uiModel is UIModel.Loading) View.VISIBLE else View.GONE

        if (uiModel is UIModel.Content) {
            adapter.recipes = uiModel.recipes
        }
    }
}