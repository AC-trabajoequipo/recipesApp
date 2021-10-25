package com.actrabajoequipo.recipesapp.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewModel : SettingsViewModel
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()
        setListeners()
        setObservers()
    }


    private fun setListeners() {

        binding.okButtonEditUsername.setOnClickListener {
            var newUsername = binding.edittextEditUsername.text.toString().trim()

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.edit_username_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                        dialog, which ->    viewModel.editUserName(newUsername)
                    binding.progressBarSettings.visibility = View.VISIBLE
                    binding.settingsLayout.visibility = View.INVISIBLE

                }
                .show()
        }

        binding.okButtonEditEmail.setOnClickListener {
            var newEmail = binding.edittextEditEmail.text.toString().trim()

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.edit_email_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                        dialog, which ->    viewModel.editEmail(newEmail)
                    binding.progressBarSettings.visibility = View.VISIBLE
                    binding.settingsLayout.visibility = View.INVISIBLE
                }
                .show()
        }

        binding.okButtonEditPassword.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.edit_password_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                        dialog, which ->    viewModel.editPassword()
                        binding.progressBarSettings.visibility = View.VISIBLE
                        binding.settingsLayout.visibility = View.INVISIBLE
                }
                .show()
        }

        binding.deleteUserButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_user_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                        dialog, which ->    viewModel.deleteUser()
                    binding.progressBarSettings.visibility = View.VISIBLE
                    binding.settingsLayout.visibility = View.INVISIBLE
                }
                .show()
        }

    }


    private fun setObservers(){

        viewModel.currentUser.observe(this,  Observer {
            binding.textviewEditUsername.text = it.name
            binding.textviewEditEmail.text = it.email
            binding.progressBarSettings.visibility = View.INVISIBLE
            binding.settingsLayout.visibility = View.VISIBLE
        })

        viewModel.resultEditUsername.observe(this, Observer {
            when(it){
                is SettingsViewModel.ResultEditUsername.UsernameEditedSuccessfully -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.message_edit_username_successfully, Toast.LENGTH_LONG).show()
                    onBackPressed()
                }
                is SettingsViewModel.ResultEditUsername.UsernameNoEdited -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.error_message_edit_username, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.resultEditEmail.observe(this, Observer {
            when(it){
                is SettingsViewModel.ResultEditEmail.EmailEditedSuccessfully -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.message_edit_email_successfully, Toast.LENGTH_LONG).show()
                    val intentFormRecipeActivity = Intent(this, MainActivity::class.java)
                    startActivity(intentFormRecipeActivity)
                }
                is SettingsViewModel.ResultEditEmail.EmailNoEdited -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.error_message_edit_email, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.resultEditPassword.observe(this, Observer {
            when(it){
                is SettingsViewModel.ResultEditPassword.PasswordEditedSuccessfully -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.message_edit_password_successfully, Toast.LENGTH_LONG).show()
                    val intentFormRecipeActivity = Intent(this, MainActivity::class.java)
                    startActivity(intentFormRecipeActivity)
                }
                is SettingsViewModel.ResultEditPassword.PasswordNoEdited -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.error_message_edit_password, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.resultDeleteUser.observe(this, Observer {
            when(it){
                is SettingsViewModel.ResultDeleteUser.DeleteUserSuccessfully -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.message_delete_user_successfully, Toast.LENGTH_LONG).show()
                    val intentFormRecipeActivity = Intent(this, MainActivity::class.java)
                    startActivity(intentFormRecipeActivity)
                }
                is SettingsViewModel.ResultDeleteUser.NoDeleteUser -> {
                    binding.progressBarSettings.visibility = View.INVISIBLE
                    binding.settingsLayout.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.error_message_delete_user, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


}