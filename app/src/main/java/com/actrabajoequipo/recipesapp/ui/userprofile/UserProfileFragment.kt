package com.actrabajoequipo.recipesapp.ui.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.databinding.FragmentUserprofileBinding
import com.actrabajoequipo.recipesapp.ui.login.LoginActivity
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.ui.settings.SettingsActivity
import com.actrabajoequipo.recipesapp.ui.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth

class UserProfileFragment : Fragment() {

    private lateinit var userProfileViewModel: UserProfileViewModel
    private var _binding: FragmentUserprofileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userProfileViewModel =
            ViewModelProvider(this).get(UserProfileViewModel::class.java)

        _binding = FragmentUserprofileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(fbAuth.currentUser != null){
            binding.nadie.text= fbAuth.currentUser?.email.toString()
            binding.buttonsProfile.visibility = View.INVISIBLE
            binding.userName.visibility = View.VISIBLE
        }

        binding.buttonSignin.setOnClickListener {
            val intent = Intent(context, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignout.setOnClickListener {
            signout()
        }

        binding.buttonSettings.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }

        return root
    }


    private fun signout() {
        Toast.makeText(context, "SESIÓN CERRADA CORRECTAMENTE :)", Toast.LENGTH_LONG).show()
        fbAuth.signOut()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}