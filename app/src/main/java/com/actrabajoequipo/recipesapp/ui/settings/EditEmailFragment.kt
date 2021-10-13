package com.actrabajoequipo.recipesapp.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.FragmentEditEmailBinding
import com.actrabajoequipo.recipesapp.databinding.FragmentSettingsBinding


class EditEmailFragment : Fragment() {

    private lateinit var binding: FragmentEditEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentEditEmailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }


}