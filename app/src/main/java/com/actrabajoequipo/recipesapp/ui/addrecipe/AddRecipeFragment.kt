package com.actrabajoequipo.recipesapp.ui.addrecipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentAddrecipeBinding
import com.actrabajoequipo.recipesapp.FormRecipeActivity
import com.actrabajoequipo.recipesapp.ui.userprofile.UserProfileFragment
import com.google.firebase.auth.FirebaseAuth

class AddRecipeFragment : Fragment() {

    private var fireBaseAuth: FirebaseAuth? = null

    private lateinit var viewModel: AddRecipeViewModel
    private lateinit var binding: FragmentAddrecipeBinding
    private var fauth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_addrecipe, container, false)
        viewModel =
            ViewModelProvider(this).get(AddRecipeViewModel::class.java)

        binding.btnAddRecipe.setOnClickListener {
            Log.d("Debug", "Entra dentro del botón")
            if (fauth.currentUser != null) {
                Log.d("USER", fauth!!.currentUser!!.uid.toString())
                val intentFormRecipeActivity = Intent(activity, FormRecipeActivity::class.java)
                startActivity(intentFormRecipeActivity)
            } else {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.nav_host_fragment_activity_main,
                        UserProfileFragment::class.java,
                        null,
                        null
                    )
                    ?.commit()
            }
        }

        binding.lifecycleOwner = this
        return binding.root
    }
}