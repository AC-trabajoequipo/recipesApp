package com.actrabajoequipo.recipesapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.actrabajoequipo.recipesapp.databinding.FragmentEditUsernameBinding
import com.actrabajoequipo.recipesapp.ui.login.LoginViewModel


class EditUsernameFragment : Fragment() {

    private lateinit var binding: FragmentEditUsernameBinding
    private lateinit var viewModel : EditUsernameViewModel

    private lateinit var username :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEditUsernameBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {
        binding.textviewCurrentUsername.text = viewModel.getCurrentUsername()
        setListeners()

        return binding.root
    }


    private fun setListeners() {
        binding.okButton.setOnClickListener {
            username = binding.edittextNewUsername.toString().trim()
        }
    }

}