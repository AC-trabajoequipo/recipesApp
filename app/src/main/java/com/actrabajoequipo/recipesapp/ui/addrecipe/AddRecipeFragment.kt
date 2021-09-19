package com.actrabajoequipo.recipesapp.ui.addrecipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentAddrecipeBinding
import com.actrabajoequipo.recipesapp.FormRecipeActivity

class AddRecipeFragment : Fragment() {

    private lateinit var viewModel: AddRecipeViewModel
    private lateinit var binding: FragmentAddrecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_addrecipe, container, false)
        viewModel =
            ViewModelProvider(this).get(AddRecipeViewModel::class.java)


        binding.btnAddRecipe.setOnClickListener {
            //usuario logado
            //if ()
            //else
            //para poder escribir una receta primero tienes que logarte o registrate si todavía no lo estas
            val intentFormRecipeActivity = Intent(activity, FormRecipeActivity::class.java)
            startActivity(intentFormRecipeActivity)
        }

        binding.lifecycleOwner = this
        return binding.root
    }
}