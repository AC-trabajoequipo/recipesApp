package com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityUsernameForGoogleAccountBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.actrabajoequipo.recipesapp.ui.login.UsernameForGoogleAccountComponent
import com.actrabajoequipo.recipesapp.ui.login.UsernameForGoogleAccountModule
import com.actrabajoequipo.recipesapp.ui.signup.SignupComponent
import com.actrabajoequipo.recipesapp.ui.signup.SignupModule
import com.actrabajoequipo.recipesapp.ui.signup.SignupViewModel

class UsernameForGoogleAccountActivity : AppCompatActivity() {


    private lateinit var binding: ActivityUsernameForGoogleAccountBinding
    private lateinit var component: UsernameForGoogleAccountComponent
    private val usernameForGoogleAccountViewModel: UsernameForGoogleAccountViewModel by lazy {
        getViewModel { component.usernameForGoogleAccountViewModel }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        component = app.component.plus(UsernameForGoogleAccountModule())
        super.onCreate(savedInstanceState)
        binding = ActivityUsernameForGoogleAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.buttonOkUsernameForGoogleAccount.setOnClickListener {
            var username = binding.usernameForGoogleAccount.text.toString().trim()
            usernameForGoogleAccountViewModel.setUsername(username)
        }
    }

    private fun setObservers(){
        usernameForGoogleAccountViewModel.resultSetUsername.observe(this, Observer {
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