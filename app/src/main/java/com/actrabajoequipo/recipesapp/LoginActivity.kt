package com.actrabajoequipo.recipesapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.actrabajoequipo.recipesapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    private val fbAuth = FirebaseAuth.getInstance()
    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setListeners()
    }

    private fun setListeners() {
        binding!!.buttonLogin.setOnClickListener {
            login()
        }

        binding!!.buttonLoginGoogle.setOnClickListener {
            loginWithGoogleAccount()
        }
    }


    private fun login(){
        var email = binding!!.ETemail.text
        var password = binding!!.ETpassword.text

        if(email.length > 0 && password.length > 0) {
            //COMPROBAMOS LAS CREDENCIALES DEL USER
            fbAuth.signInWithEmailAndPassword(email.toString().trim(), password.toString().trim())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = fbAuth.currentUser
                        if(user!!.isEmailVerified){
                            Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, R.string.email_no_confirmed, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, R.string.email_or_password_failed, Toast.LENGTH_LONG).show()
                    }
                }
        }else{
            Toast.makeText(this, R.string.fill_in_the_fields, Toast.LENGTH_LONG).show()
        }

    }

    private fun loginWithGoogleAccount() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this,googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    fbAuth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }catch (e: ApiException){
                Toast.makeText(this, "", Toast.LENGTH_LONG).show()
            }

        }
    }


}