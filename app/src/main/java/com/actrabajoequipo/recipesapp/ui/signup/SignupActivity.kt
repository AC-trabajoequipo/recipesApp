package com.actrabajoequipo.recipesapp.ui.signup

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivitySigninBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var component: SignupComponent
    private val signupViewModel: SignupViewModel by lazy {
        getViewModel { component.signupViewModel }
    }

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passwordConfirm: String

    override fun onCreate(savedInstanceState: Bundle?) {
        component = app.component.plus(SignupModule())
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setListeners()

        signupViewModel.registered.observe(this, Observer {
            when (it) {
                is SignupViewModel.UiSignup.UnconfirmedEmail -> {
                    Toast.makeText(this, R.string.confirm_your_email, Toast.LENGTH_LONG).show()
                    onBackPressed()
                }
                is SignupViewModel.UiSignup.EmailAlreadyRegistered -> Toast.makeText(
                    this,
                    R.string.email_already_registered,
                    Toast.LENGTH_LONG
                ).show()
                is SignupViewModel.UiSignup.PasswordRequirements -> Toast.makeText(
                    this,
                    R.string.password_requirement,
                    Toast.LENGTH_LONG
                ).show()
                is SignupViewModel.UiSignup.PasswordsDoNotMatch -> Toast.makeText(
                    this,
                    R.string.passwords_do_not_match,
                    Toast.LENGTH_LONG
                ).show()
                is SignupViewModel.UiSignup.FillinFields -> Toast.makeText(
                    this,
                    R.string.fill_in_alls_fields,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setListeners() {
        binding.buttonSignin.setOnClickListener {
            name = binding.ETname.text.toString()
            email = binding.ETemail.text.toString()
            password = binding.ETpassword.text.toString()
            passwordConfirm = binding.ETpasswordConfirm.text.toString()

            signupViewModel.signup(name, email, password, passwordConfirm)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}