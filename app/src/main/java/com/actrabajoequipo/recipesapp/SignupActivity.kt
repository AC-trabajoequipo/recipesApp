package com.actrabajoequipo.recipesapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.actrabajoequipo.recipesapp.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

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


        if(name.length > 1 && email.length > 1 && password.length > 5 && passwordConfirm.length > 5){
            if( password.toString().trim().equals(passwordConfirm.toString().trim())) {
                if (password.matches(Regex(".*[a-z].*"))
                        && password.matches(Regex(".*[A-Z].*"))
                        && password.matches(Regex(".*[0-9].*"))){

                    //CREAMOS EL USER
                    fbAuth.createUserWithEmailAndPassword(email.toString().trim(), password.toString().trim())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = fbAuth.currentUser
                            //ENVIAMOS MAIL DE CONFIRMACION
                            user?.sendEmailVerification()
                            Toast.makeText(this, R.string.confirm_your_email, Toast.LENGTH_LONG).show()
                        }else Toast.makeText(this, R.string.email_already_registered, Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, R.string.requirement_password, Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, R.string.passwords_do_not_match, Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, R.string.fill_in_alls_fields, Toast.LENGTH_LONG).show()
        }
    }


}