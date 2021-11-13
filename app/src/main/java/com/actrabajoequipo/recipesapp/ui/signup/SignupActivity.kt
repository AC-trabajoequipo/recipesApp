package com.actrabajoequipo.recipesapp.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivitySigninBinding
import com.actrabajoequipo.recipesapp.ui.Scope
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity(), Scope by Scope.Impl() {


    private lateinit var viewModel : SignupViewModel
    private lateinit var binding: ActivitySigninBinding
    private val fbAuth = FirebaseAuth.getInstance()

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passwordConfirm: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()
        setListeners()

        viewModel.registrado.observe(this, Observer {
            when(it){
                is SignupViewModel.UiSignup.UnconfirmedEmail -> {
                    Toast.makeText(this, R.string.confirm_your_email, Toast.LENGTH_LONG).show()
                    onBackPressed()
                }
                is SignupViewModel.UiSignup.EmailAlreadyRegistered -> Toast.makeText(this, R.string.email_already_registered, Toast.LENGTH_LONG).show()
                is SignupViewModel.UiSignup.PasswordRequirements -> Toast.makeText(this, R.string.password_requirement, Toast.LENGTH_LONG).show()
                is SignupViewModel.UiSignup.PasswordsDoNotMatch -> Toast.makeText(this, R.string.passwords_do_not_match, Toast.LENGTH_LONG).show()
                is SignupViewModel.UiSignup.FillinFields -> Toast.makeText(this, R.string.fill_in_alls_fields, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setListeners() {
        binding.buttonSignin.setOnClickListener {
            name = binding.ETname.text.toString()
            email = binding.ETemail.text.toString()
            password = binding.ETpassword.text.toString()
            passwordConfirm = binding.ETpasswordConfirm.text.toString()

            viewModel.signup(name, email, password, passwordConfirm)
        }
    }


}