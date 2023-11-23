package com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityUsernameForGoogleAccountBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.actrabajoequipo.recipesapp.ui.login.UsernameForGoogleAccountComponent
import com.actrabajoequipo.recipesapp.ui.login.UsernameForGoogleAccountModule

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
            val username = binding.usernameForGoogleAccount.text.toString().trim()
            usernameForGoogleAccountViewModel.setUsername(username)
        }
    }

    private fun setObservers(){
        usernameForGoogleAccountViewModel.resultSetUsername.observe(this) {
            when (it) {
                is UsernameForGoogleAccountViewModel.ResultSetUsername.SetUsernameSuccessfully -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                is UsernameForGoogleAccountViewModel.ResultSetUsername.SetUsernameNoEdited -> {
                    Toast.makeText(
                        this,
                        R.string.error_message_set_username_for_google_account,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}