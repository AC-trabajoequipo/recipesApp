package com.actrabajoequipo.recipesapp.ui.addrecipe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.databinding.FragmentAddrecipeBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel

class AddRecipeFragment : Fragment() {

    private lateinit var binding: FragmentAddrecipeBinding
    private lateinit var component: AddRecipeComponent
    private val viewModel by lazy { getViewModel { component.addRecipeViewModel } }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = context.app.component.plus(AddRecipeModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddrecipeBinding.inflate(layoutInflater)

        binding.btnAddRecipe.setOnClickListener {
            viewModel.isUserLogged()
        }
        
        viewModel.userLoggedState.observe(viewLifecycleOwner, {
            when(it){
                is AddRecipeViewModel.UserLogged.Logged ->
                    findNavController().navigate(AddRecipeFragmentDirections.actionNavigationAddRecipeToFormRecipeActivity())

                is AddRecipeViewModel.UserLogged.NotLogged -> {
                    Toast.makeText(context, "Necesitas estar logado para subir recetas", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(AddRecipeFragmentDirections.actionNavigationAddrecipeToNavigationUserprofile())
                }
            }
        })

        return binding.root
    }
}