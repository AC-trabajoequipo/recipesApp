package com.actrabajoequipo.recipesapp.ui.login.forgotPassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityForgotPasswordBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.actrabajoequipo.recipesapp.ui.login.ForgotPasswordComponent
import com.actrabajoequipo.recipesapp.ui.login.ForgotPasswordModule

class ForgotPasswordActivity : AppCompatActivity() {


    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var component: ForgotPasswordComponent
    private val forgotPasswordViewModel: ForgotPasswordViewModel by lazy {
        getViewModel { component.forgotPasswordViewModel }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        component = app.component.plus(ForgotPasswordModule())
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.buttonOkEmailForgotPassword.setOnClickListener {
            val email = binding.recoveryEmail.text.toString().trim()
            forgotPasswordViewModel.editPassword(email)
        }
    }

    private fun setObservers(){
        forgotPasswordViewModel.resultEditPassword.observe(this, Observer {
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