package com.actrabajoequipo.recipesapp.ui.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.actrabajoequipo.recipesapp.databinding.FragmentAddrecipeBinding

class AddRecipeFragment : Fragment() {

    private lateinit var addRecipeViewModel: AddRecipeViewModel
    private var _binding: FragmentAddrecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addRecipeViewModel =
            ViewModelProvider(this).get(AddRecipeViewModel::class.java)

        _binding = FragmentAddrecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAddRecipe
        addRecipeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}