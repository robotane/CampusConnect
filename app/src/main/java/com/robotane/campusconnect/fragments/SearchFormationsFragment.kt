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

class SearchFormationsFragment: Fragment(){

    private lateinit var binding: FragmentSearchFormationBinding
/*    private val viewModel: FormationsSearchViewModel by viewModels {
        FormationsSearchViewModelFactory(this, arguments)
    }*/
    val viewModel: FormationsSearchViewModel by viewModels()

//    private lateinit var viewModel: FormationsSearchViewModel

    companion object {
        fun newInstance() = SearchFormationsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_formation, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this)[FormationsSearchViewModel::class.java]
        binding.viewmodel = viewModel

/*        //Set onClickListener the search button
        binding.fragmentSearchFormationSearchBtn
            .setOnClickListener{
                val intent = Intent(activity, FormationsResultActivity::class.java)
                intent.putExtra(Constants.BAC_TYPE, viewModel.bacType.value)
                intent.putExtra(Constants.FORMATIONS, viewModel.formation.value)
                intent.putExtra(Constants.TOWNS, viewModel.towns.value)
                intent.putExtra(Constants.UNIVERSITY_TYPE,
                    viewModel.universityType.value?.let { UniversityType.getByItemID(it) })
                startActivity(intent)
            }*/
    }
}