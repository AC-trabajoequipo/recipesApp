package com.actrabajoequipo.recipesapp.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentNotificationsBinding
import com.actrabajoequipo.recipesapp.LoginActivity
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.SigninActivity
import com.google.firebase.auth.FirebaseAuth

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(fbAuth.currentUser != null){
            binding.nadie.text= fbAuth.currentUser!!.email.toString()
            binding.buttonsProfile.visibility = View.INVISIBLE
            binding.userName.visibility = View.VISIBLE
        }

        binding.buttonSignin.setOnClickListener {
            val intent = Intent(context, SigninActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignout.setOnClickListener {
            signout()
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