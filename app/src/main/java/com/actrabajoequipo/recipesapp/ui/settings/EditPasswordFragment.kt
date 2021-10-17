package com.actrabajoequipo.recipesapp.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentEditPasswordBinding


class EditPasswordFragment : Fragment() {

    private lateinit var binding: FragmentEditPasswordBinding
    private lateinit var viewModel : EditPasswordViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEditPasswordBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get()
        navController = findNavController()

        viewModel.result.observe(this, Observer {
            when(it){
                is EditPasswordViewModel.ResultEditPassword.passwordEditedSuccessfully -> {
                    Toast.makeText(activity, R.string.message_edit_password_successfully, Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.action_editPasswordFragment_to_settingsFragment)
                }
                is EditPasswordViewModel.ResultEditPassword.passwordNoEdited -> {
                    Toast.makeText(activity, R.string.error_message_edit_password, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.okButtonEditEmail.setOnClickListener {

            AlertDialog.Builder(activity)
                .setMessage(getString(R.string.edit_password_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                    dialog, which ->
                        viewModel.editPassword()
                }
                .show()
        }
    }

}