package com.actrabajoequipo.recipesapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityForgotPasswordBinding
import com.actrabajoequipo.recipesapp.databinding.ActivitySettingsBinding
import com.actrabajoequipo.recipesapp.ui.settings.SettingsViewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var viewModel : ForgotPasswordViewModel
    private lateinit var binding: ActivityForgotPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.buttonOkEmailForgotPassword.setOnClickListener {
            var email = binding.forgotPasswordEmail.text.toString().trim()
            viewModel.editPassword(email)
        }
    }

    private fun setObservers(){
        viewModel.resultEditPassword.observe(this, Observer {
            when(it){
                is ForgotPasswordViewModel.ResultEditPassword.PasswordEditedSuccessfully -> {
                    Toast.makeText(this, R.string.message_edit_password_successfully, Toast.LENGTH_LONG).show()
                    onBackPressed()
                }
                is ForgotPasswordViewModel.ResultEditPassword.PasswordNoEdited -> {
                    Toast.makeText(this, R.string.error_message_edit_password, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}