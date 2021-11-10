package com.actrabajoequipo.recipesapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityForgotPasswordBinding
import com.actrabajoequipo.recipesapp.databinding.ActivityUsernameForGoogleAccountBinding

class UsernameForGoogleAccountActivity : AppCompatActivity() {

    private lateinit var viewModel : UsernameForGoogleAccountViewModel
    private lateinit var binding: ActivityUsernameForGoogleAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsernameForGoogleAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.buttonOkUsernameForGoogleAccount.setOnClickListener {
            var username = binding.usernameForGoogleAccount.text.toString().trim()
            viewModel.setUsername(username)
        }
    }

    private fun setObservers(){
        viewModel.resultSetUsername.observe(this, Observer {
            when(it){
                is UsernameForGoogleAccountViewModel.ResultSetUsername.SetUsernameSuccessfully -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                is UsernameForGoogleAccountViewModel.ResultSetUsername.SetUsernameNoEdited -> {
                    Toast.makeText(this, R.string.error_message_set_username_for_google_account, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}