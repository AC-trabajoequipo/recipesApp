package com.actrabajoequipo.recipesapp.ui.addrecipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentAddrecipeBinding
import com.actrabajoequipo.recipesapp.ui.formrecipe.FormRecipeActivity
import com.actrabajoequipo.recipesapp.ui.home.HomeFragmentDirections
import com.actrabajoequipo.recipesapp.ui.userprofile.UserProfileFragment
import com.google.firebase.auth.FirebaseAuth

class AddRecipeFragment : Fragment() {

    private lateinit var viewModel: AddRecipeViewModel
    private lateinit var binding: FragmentAddrecipeBinding
    private var fauth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddrecipeBinding.inflate(layoutInflater)
        viewModel =
            ViewModelProvider(this).get(AddRecipeViewModel::class.java)

        binding.btnAddRecipe.setOnClickListener {
            if (fauth.currentUser != null) {
                /*val intentFormRecipeActivity = Intent(activity, FormRecipeActivity::class.java)
                startActivity(intentFormRecipeActivity)*/
                findNavController().navigate(AddRecipeFragmentDirections.actionNavigationAddRecipeToFormRecipeActivity())
            } else {
                Toast.makeText(context, "Necesitas estar logado para subir recetas", Toast.LENGTH_SHORT).show()
                /*activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.nav_host_fragment_activity_main,
                        UserProfileFragment::class.java,
                        null,
                        null
                    )
                    ?.commit()*/
                findNavController().navigate(AddRecipeFragmentDirections.actionNavigationAddrecipeToNavigationUserprofile())
            }
        }

        return binding.root
    }
}