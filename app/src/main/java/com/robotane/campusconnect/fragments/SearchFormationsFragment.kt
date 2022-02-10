package com.robotane.campusconnect.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.robotane.campusconnect.R
import com.robotane.campusconnect.databinding.FragmentSearchFormationBinding
import com.robotane.campusconnect.ui.FormationsSearchViewModel

class SearchFormationsFragment : Fragment() {

    private lateinit var binding: FragmentSearchFormationBinding
    val viewModel: FormationsSearchViewModel by viewModels()

    companion object {
        fun newInstance() = SearchFormationsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_formation, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
    }
}