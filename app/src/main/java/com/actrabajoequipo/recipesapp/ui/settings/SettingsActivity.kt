package com.actrabajoequipo.recipesapp.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivitySettingsBinding
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var component: SettingsComponent
    private val settingsViewModel: SettingsViewModel by lazy {
        getViewModel { component.settingsViewModel }
    }

    private var editUsernameFlag :Boolean = false
    private var editEmailFlag :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        component = app.component.plus(SettingsModule())
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setObservers()
    }


    private fun setListeners() {

        binding.okButtonEditUsername.setOnClickListener {

            if(!editUsernameFlag){
                binding.textviewEditUsername.visibility = View.INVISIBLE
                binding.edittextEditUsername.text = binding.textviewEditUsername.text as Editable?
                binding.edittextEditUsername.isFocusableInTouchMode = true
                binding.edittextEditUsername.isFocusable = true
                binding.okButtonEditUsername.setImageResource(R.mipmap.ic_check)
                editUsernameFlag = true
            }else{
                var newUsername = binding.edittextEditUsername.text.toString().trim()

                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.edit_username_confirmation_question))
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(getString(R.string.yes)){
                            dialog, which ->    settingsViewModel.editUserName(newUsername)
                        binding.progressBarSettings.visibility = View.VISIBLE
                        binding.settingsLayout.visibility = View.INVISIBLE

                    }
                    .show()
            }

        }

        binding.okButtonEditEmail.setOnClickListener {

            if(!editEmailFlag){
                binding.textviewEditEmail.visibility = View.INVISIBLE
                binding.edittextEditEmail.text = binding.textviewEditEmail.text as Editable?
                binding.edittextEditEmail.isFocusableInTouchMode = true
                binding.edittextEditEmail.isFocusable = true
                binding.okButtonEditEmail.setImageResource(R.mipmap.ic_check)
                editEmailFlag = true
            }else {
                var newEmail = binding.edittextEditEmail.text.toString().trim()

                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.edit_email_confirmation_question))
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        settingsViewModel.editEmail(newEmail)
                        binding.progressBarSettings.visibility = View.VISIBLE
                        binding.settingsLayout.visibility = View.INVISIBLE
                    }
                    .show()
            }
        }

        binding.okButtonEditPassword.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.edit_password_confirmation_question))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(getString(R.string.yes)){
                        dialog, which ->    settingsViewModel.editPassword()
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
                        dialog, which ->    settingsViewModel.deleteUser()
                    binding.progressBarSettings.visibility = View.VISIBLE
                    binding.settingsLayout.visibility = View.INVISIBLE
                }
                .show()
        }

        //TODO Remove
        binding.button2.setOnClickListener {
            settingsViewModel.pruebaaa()
        }
    }

    private fun setObservers(){

        settingsViewModel.currentUser.observe(this) {
            binding.textviewEditUsername.setText(it.name)
            binding.textviewEditEmail.setText(it.email)
            binding.progressBarSettings.visibility = View.INVISIBLE
            binding.settingsLayout.visibility = View.VISIBLE
        }

        settingsViewModel.resultEditUsername.observe(this, Observer {
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

        settingsViewModel.resultEditEmail.observe(this, Observer {
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

        settingsViewModel.resultEditPassword.observe(this, Observer {
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

        settingsViewModel.resultDeleteUser.observe(this, Observer {
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