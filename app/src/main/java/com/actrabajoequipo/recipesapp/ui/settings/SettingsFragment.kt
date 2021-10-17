package com.actrabajoequipo.recipesapp.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityLoginBinding
import com.actrabajoequipo.recipesapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel : SettingsViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get()
        navController = findNavController()

        viewModel.result.observe(this, Observer {
            when(it){
                is SettingsViewModel.ResultDeleteUser.deleteUserSuccessfully -> {
                    Toast.makeText(activity, R.string.message_delete_user_successfully, Toast.LENGTH_LONG).show()
                    val intentFormRecipeActivity = Intent(activity, MainActivity::class.java)
                    startActivity(intentFormRecipeActivity)
                }
                is SettingsViewModel.ResultDeleteUser.noDeleteUser -> {
                    Toast.makeText(activity, R.string.error_message_delete_user, Toast.LENGTH_LONG).show()
                }
            }
        })
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

            deleteUserButton.setOnClickListener {
                viewModel.deleteUser()
            }
        }



    }


}