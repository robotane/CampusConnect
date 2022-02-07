package com.robotane.campusconnect.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robotane.campusconnect.R
import com.robotane.campusconnect.ui.FiliereApplication
import com.robotane.campusconnect.ui.FiliereListAdapter
import com.robotane.campusconnect.ui.FiliereViewModel
import com.robotane.campusconnect.ui.FiliereViewModelFactory
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.UniversityType

class FormationsResultFragment: Fragment() {

    companion object {
        fun newInstance() = FormationsResultFragment()
    }

    val filiereViewModel: FiliereViewModel by activityViewModels {
        FiliereViewModelFactory((activity?.application as FiliereApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_formations_result, container, false)
        val intent = activity?.intent
        val bacType = intent?.getStringExtra(Constants.BAC_TYPE)
        val universityType = intent?.getSerializableExtra(Constants.UNIVERSITY_TYPE) as UniversityType?
        val formations = intent?.getStringExtra(Constants.FORMATIONS)
        val towns = intent?.getStringExtra(Constants.TOWNS)

        val recyclerView = inflate.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FiliereListAdapter()

        filiereViewModel.allWords.observe(viewLifecycleOwner, { filieres ->
            // Update the cached copy of the words in the adapter.
            filieres?.let { adapter.submitList(it) }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return inflate
    }

}