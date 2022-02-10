package com.robotane.campusconnect.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout
import com.robotane.campusconnect.FormationsResultActivity
import com.robotane.campusconnect.R
import com.robotane.campusconnect.databinding.FragmentSearchFormationBinding
import com.robotane.campusconnect.ui.*
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.UniversityType

class SearchFormationsFragment: Fragment(){

    private lateinit var binding: FragmentSearchFormationBinding
/*    private val viewModel: FormationsSearchPayloadViewModel by activityViewModels {
        FormationsSearchPayloadViewModelFactory()
    }*/
    private lateinit var viewModel: FormationsSearchPayloadViewModel

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

        viewModel = ViewModelProvider(this).get(FormationsSearchPayloadViewModel::class.java)

        //Set onClickListener the search button

        binding.fragmentSearchFormationSearchBtn
            .setOnClickListener{
                val universityType = when (binding.fragmentSearchFormationUniversityStatusRdbtnGroup.checkedRadioButtonId){
                    R.id.fragment_search_formation_all_university_rdbtn -> UniversityType.ANY

                    R.id.fragment_search_formation_private_university_rdbtn -> UniversityType.PRIVATE

                    R.id.fragment_search_formation_public_university_rdbtn -> UniversityType.PUBLIC

                    else -> UniversityType.ANY
                }
                val intent = Intent(activity, FormationsResultActivity::class.java)
                intent.putExtra(Constants.BAC_TYPE, binding.fragmentSearchFormationSerieBac.editText?.text.toString())
                intent.putExtra(Constants.FORMATIONS, binding.fragmentSearchFormationFilieres.editText?.text.toString())
                intent.putExtra(Constants.TOWNS, binding.fragmentSearchFormationVilles.editText?.text.toString())
                intent.putExtra(Constants.UNIVERSITY_TYPE, universityType)
                startActivity(intent)
            }
    }
}