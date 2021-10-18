package com.actrabajoequipo.recipesapp.ui.settings

import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentEditEmailBinding
import com.actrabajoequipo.recipesapp.databinding.FragmentEditUsernameBinding
import com.actrabajoequipo.recipesapp.databinding.FragmentSettingsBinding
import com.actrabajoequipo.recipesapp.ui.formrecipe.FormRecipeActivity
import com.actrabajoequipo.recipesapp.ui.signup.SignupViewModel
import com.google.firebase.auth.FirebaseAuth


class EditEmailFragment : Fragment() {

    private lateinit var binding: FragmentEditEmailBinding
    private lateinit var viewModel : EditEmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEditEmailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get()

        viewModel.currentEmail.observe(this,  Observer {
            binding.textviewCurrentEmail.text = it
            binding.progressBarEditEmail.visibility = View.INVISIBLE
            binding.editEmailLayout.visibility = View.VISIBLE
        })

        viewModel.result.observe(this, Observer {
            when(it){
                is EditEmailViewModel.ResultEditEmail.emailEditedSuccessfully -> {
                    binding.progressBarEditEmail.visibility = View.INVISIBLE
                    Toast.makeText(activity, R.string.message_edit_email_successfully, Toast.LENGTH_LONG).show()
                    val intentFormRecipeActivity = Intent(activity, MainActivity::class.java)
                    startActivity(intentFormRecipeActivity)
                }
                is EditEmailViewModel.ResultEditEmail.emailNoEdited -> {
                    binding.progressBarEditEmail.visibility = View.INVISIBLE
                    binding.editEmailLayout.visibility = View.VISIBLE
                    Toast.makeText(activity, R.string.error_message_edit_email, Toast.LENGTH_LONG).show()
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
            var newEmail = binding.edittextNewEmail.text.toString().trim()

            AlertDialog.Builder(activity)
                .setMessage(getString(R.string.edit_email_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                    dialog, which ->
                        binding.progressBarEditEmail.visibility = View.VISIBLE
                        binding.editEmailLayout.visibility = View.INVISIBLE
                        viewModel.editEmail(newEmail)
                }
                .show()
        }
    }

}