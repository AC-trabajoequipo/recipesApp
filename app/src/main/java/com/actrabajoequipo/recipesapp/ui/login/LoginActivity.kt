package com.actrabajoequipo.recipesapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivityLoginBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.actrabajoequipo.recipesapp.ui.login.forgotPassword.ForgotPasswordActivity
import com.actrabajoequipo.recipesapp.ui.login.usernameForGoogleAccount.UsernameForGoogleAccountActivity
import com.actrabajoequipo.recipesapp.ui.signup.SignupActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var component: LoginComponent
    private val loginViewModel: LoginViewModel by lazy {
        getViewModel { component.loginViewModel }
    }

    private val GOOGLE_SIGN_IN = 100

    private lateinit var email: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        component = app.component.plus(LoginModule())
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()

        loginViewModel.loginModel.observe(this, Observer {
            when (it) {
                is LoginViewModel.UiLogin.Success -> {
                    Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                is LoginViewModel.UiLogin.UnconfirmedEmail -> Toast.makeText(
                    this,
                    R.string.email_no_confirmed,
                    Toast.LENGTH_LONG
                ).show()
                is LoginViewModel.UiLogin.WrongEmailOrPassword -> Toast.makeText(
                    this,
                    R.string.email_or_password_failed,
                    Toast.LENGTH_LONG
                ).show()
                is LoginViewModel.UiLogin.FillInFields -> Toast.makeText(
                    this,
                    R.string.fill_in_the_fields,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        loginViewModel.logeadoGoogle.observe(this, Observer {
            when(it){
                is LoginViewModel.UiLoginWithGoogleAccount.Success -> {
                    val intent = Intent(this, UsernameForGoogleAccountActivity::class.java)
                    startActivity(intent)
                }
                is LoginViewModel.UiLoginWithGoogleAccount.NotSuccess -> Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show()
                is LoginViewModel.UiLoginWithGoogleAccount.Error -> Toast.makeText(this, R.string.login_error3, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setListeners() {
        binding.buttonLogin.setOnClickListener {
            email = binding.ETemail.text.toString()
            password = binding.ETpassword.text.toString()

            loginViewModel.login(email, password)
        }

        binding.buttonLoginGoogle.setOnClickListener {
            loginWithGoogleAccount()
        }

        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginWithGoogleAccount() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        loginViewModel.postLoginWithGoogleAccount(requestCode, resultCode, data)
    }
}