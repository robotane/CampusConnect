package com.robotane.campusconnect.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robotane.campusconnect.R
import com.robotane.campusconnect.databinding.FragmentFormationsResultBinding
import com.robotane.campusconnect.ui.FiliereApplication
import com.robotane.campusconnect.ui.FiliereListAdapter
import com.robotane.campusconnect.ui.FiliereViewModel
import com.robotane.campusconnect.ui.FiliereViewModelFactory
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.UniversityType

class FormationsResultFragment: Fragment() {

    private lateinit var binding: FragmentFormationsResultBinding
    private val viewModel: FiliereViewModel by activityViewModels {
        FiliereViewModelFactory((activity?.application as FiliereApplication).repository)
    }

    companion object {
        fun newInstance() = FormationsResultFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_formations_result, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = activity?.intent
        val bacType = intent?.getStringExtra(Constants.BAC_TYPE)
        val universityType = intent?.getSerializableExtra(Constants.UNIVERSITY_TYPE) as UniversityType?
        val formations = intent?.getStringExtra(Constants.FORMATIONS)
        val towns = intent?.getStringExtra(Constants.TOWNS)

        val layoutManager = LinearLayoutManager(context)

        val filiereListAdapter = FiliereListAdapter()

        viewModel.fetchFormationsByQuery(formations.toString())

        viewModel.searchFormationsLiveData.observe(viewLifecycleOwner, { filieres ->
            // Update the cached copy of the words in the adapter.
            filieres?.let { filiereListAdapter.submitList(it) }
        })
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.hasFixedSize()
        binding.recyclerview.adapter = filiereListAdapter
        binding.viewmodel = viewModel
    }

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_formations_result, container, false)


        val recyclerView = inflate.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FiliereListAdapter()

        filiereViewModel.allWords.observe(viewLifecycleOwner, { filieres ->
            // Update the cached copy of the words in the adapter.
            filieres?.let { adapter.submitList(it) }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return inflate
    }*/

}