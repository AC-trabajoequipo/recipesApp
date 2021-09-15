package com.actrabajoequipo.recipesapp.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivitySigninBinding
import com.actrabajoequipo.recipesapp.ui.login.LoginViewModel
import com.actrabajoequipo.recipesapp.ui.login.LoginViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var viewModel : SignupViewModel
    private var binding: ActivitySigninBinding? = null
    private val fbAuth = FirebaseAuth.getInstance()

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passwordConfirm: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setListeners()
    }

    private fun setListeners() {
        binding!!.buttonSignin.setOnClickListener {
            signup()
        }
    }


    private fun signup(){
        name = binding!!.ETname.text.toString()
        email = binding!!.ETemail.text.toString()
        password = binding!!.ETpassword.text.toString()
        passwordConfirm = binding!!.ETpasswordConfirm.text.toString()

        viewModel = ViewModelProvider(
            this,
            SignupViewModelFactory(name.toString(), email.toString(),password.toString(), passwordConfirm.toString())
        ).get()
        viewModel.registrado.observe(this, Observer {
            when(it){
                is SignupViewModel.UiSignup.State1 -> Toast.makeText(this, R.string.confirm_your_email, Toast.LENGTH_LONG).show()
                is SignupViewModel.UiSignup.State2 -> Toast.makeText(this, R.string.email_already_registered, Toast.LENGTH_LONG).show()
                is SignupViewModel.UiSignup.State3 -> Toast.makeText(this, R.string.requirement_password, Toast.LENGTH_LONG).show()
                is SignupViewModel.UiSignup.State4 -> Toast.makeText(this, R.string.passwords_do_not_match, Toast.LENGTH_LONG).show()
                is SignupViewModel.UiSignup.State5 -> Toast.makeText(this, R.string.fill_in_alls_fields, Toast.LENGTH_LONG).show()
            }
        })
    }


}