package com.actrabajoequipo.recipesapp.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentEditUsernameBinding


class EditUsernameFragment : Fragment() {

    private lateinit var binding: FragmentEditUsernameBinding
    private lateinit var viewModel : EditUsernameViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEditUsernameBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get()
        navController = findNavController()

        viewModel.currentUsername.observe(this,  Observer {
            binding.textviewCurrentUsername.text = it
            binding.progressBarEditUsername.visibility = View.INVISIBLE
            binding.editUsernameLayout.visibility = View.VISIBLE
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {
        setListeners()
        return binding.root
    }


    private fun setListeners() {
        binding.okButtonEditUsername.setOnClickListener {
            var newUsername = binding.edittextNewUsername.text.toString().trim()

            AlertDialog.Builder(activity)
                .setMessage(getString(R.string.edit_username_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                    dialog, which -> viewModel.editUserName(newUsername)
                    navController.navigate(R.id.action_editUsernameFragment_to_settingsFragment)
                }
                .show()
        }
    }

}