package com.actrabajoequipo.recipesapp.ui.userprofile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentUserprofileBinding
import com.actrabajoequipo.recipesapp.model.RecipesRepository
import com.actrabajoequipo.recipesapp.ui.addrecipe.AddRecipeFragmentDirections
import com.actrabajoequipo.recipesapp.ui.app
import com.actrabajoequipo.recipesapp.ui.getViewModel
import com.actrabajoequipo.recipesapp.ui.login.LoginActivity
import com.actrabajoequipo.recipesapp.ui.settings.SettingsActivity
import com.actrabajoequipo.recipesapp.ui.signup.SignupActivity
import com.actrabajoequipo.recipesapp.ui.userprofile.adapter.RecipesAdapter
import com.google.firebase.auth.FirebaseAuth

class UserProfileFragment : Fragment() {

    private lateinit var userProfileViewModel: UserProfileViewModel
    private lateinit var adapterMyRecipes: RecipesAdapter
    private lateinit var adapterMyFavourites: RecipesAdapter
    private lateinit var binding: FragmentUserprofileBinding
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentUserprofileBinding.inflate(layoutInflater)

        userProfileViewModel =
            getViewModel { UserProfileViewModel(RecipesRepository(requireContext().app)) }

        userProfileViewModel.uiModelMyRecipes.observe(this, ::updateRecyclerMyRecipes)
        userProfileViewModel.uiModelMyFavRecipes.observe(this, ::updateRecyclerMyFavRecipes)

        userProfileViewModel.navigation.observe(this, { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    UserProfileFragmentDirections.actionNavigationProfileToDetailActivity(it.id)
                )
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapterMyRecipes = RecipesAdapter(userProfileViewModel::onRecipeClicked)
        adapterMyFavourites = RecipesAdapter(userProfileViewModel::onRecipeClicked)

        setRecipesInitialState()

        binding.recyclerMyRecipes.adapter = adapterMyRecipes
        binding.recyclerMyFavourites.adapter = adapterMyFavourites

        setHasOptionsMenu(true)

        if (fbAuth.currentUser?.uid ?: null != null) {
            binding.nadie.text = fbAuth.currentUser?.email.toString()
            binding.buttonsProfile.visibility = View.GONE
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

        return binding.root
    }

    override fun onResume() {
        setRecipesInitialState()
        super.onResume()
    }

    private fun goToSettingActivity() {
        val intent = Intent(context, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun signout() {
        Toast.makeText(context, "SESIÓN CERRADA CORRECTAMENTE :)", Toast.LENGTH_LONG).show()
        fbAuth.signOut()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        if (fbAuth.currentUser?.uid != null)
            menuInflater.inflate(R.menu.toolbar_profile_menu, menu)
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    private fun setRecipesInitialState() {
        userProfileViewModel.refresh()
    }

    private fun updateRecyclerMyRecipes(uiModelMyRecipes: UserProfileViewModel.UIModelMyRecipes) {
        binding.progress.visibility =
            if (uiModelMyRecipes is UserProfileViewModel.UIModelMyRecipes.Loading) View.VISIBLE else View.GONE

        if (uiModelMyRecipes is UserProfileViewModel.UIModelMyRecipes.ContentMyRecipes) {
            adapterMyRecipes.recipes = uiModelMyRecipes.myRecipes
        }
    }

    private fun updateRecyclerMyFavRecipes(uiModelMyFavRecipes: UserProfileViewModel.UIModelMyFavRecipes) {
        binding.progress.visibility =
            if (uiModelMyFavRecipes is UserProfileViewModel.UIModelMyFavRecipes.Loading) View.VISIBLE else View.GONE

        if (uiModelMyFavRecipes is UserProfileViewModel.UIModelMyFavRecipes.ContentMyFavourites) {
            adapterMyFavourites.recipes = uiModelMyFavRecipes.myFavRecipes
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                goToSettingActivity()
                true
            }

            R.id.action_sign_out -> {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(getString(R.string.title_confirm_signout))
                builder.setMessage(getString(R.string.message_confirm_signout))
                builder.setPositiveButton("Sí") { dialog, _ ->
                    signout()
                    dialog.cancel()
                    findNavController().navigate(UserProfileFragmentDirections.actionNavigationProfileToProfile())
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                val alert = builder.create()
                alert.show()

                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}