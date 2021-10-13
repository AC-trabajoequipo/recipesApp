package com.actrabajoequipo.recipesapp.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityLoginBinding
import com.actrabajoequipo.recipesapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        navController = findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setListeners()

        return binding.root
    }


    fun setListeners(){

        with(binding){
            editUsernameButton.setOnClickListener {
                navController.navigate(R.id.action_settingsFragment_to_editUsernameFragment)
            }

            editEmailButton.setOnClickListener {
                navController.navigate(R.id.action_settingsFragment_to_editEmailFragment)
            }

            editPasswordButton.setOnClickListener {
                navController.navigate(R.id.action_settingsFragment_to_editPasswordFragment)
            }
        }



    }


}