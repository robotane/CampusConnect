package com.robotane.campusconnect.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.robotane.campusconnect.FormationsResultActivity
import com.robotane.campusconnect.R
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.UniversityType

class SearchFormationsFragment: Fragment(){

    companion object {
        fun newInstance() = SearchFormationsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Inflate the layout of MainFragment
        val result = inflater.inflate(R.layout.fragment_search_formation, container, false)
        val bacTypeInput = result.findViewById<TextInputLayout>(R.id.fragment_search_formation_serie_bac)
        val formationsInput = result.findViewById<TextInputLayout>(R.id.fragment_search_formation_filieres)
        val townsInput = result.findViewById<TextInputLayout>(R.id.fragment_search_formation_villes)
        val universityTypeRadioGroup = result.findViewById<RadioGroup>(R.id.fragment_search_formation_university_status_rdbtn_group)

        //Set onClickListener the search button

        result.findViewById<Button>(R.id.fragment_search_formation_search_btn)
            .setOnClickListener{
                val universityType = when (universityTypeRadioGroup.checkedRadioButtonId){
                    R.id.fragment_search_formation_all_university_rdbtn -> UniversityType.ANY

                    R.id.fragment_search_formation_private_university_rdbtn -> UniversityType.PRIVATE

                    R.id.fragment_search_formation_public_university_rdbtn -> UniversityType.PUBLIC

                    else -> UniversityType.ANY
                }
                val intent = Intent(activity, FormationsResultActivity::class.java)
                intent.putExtra(Constants.BAC_TYPE, bacTypeInput.editText?.text.toString())
                intent.putExtra(Constants.FORMATIONS, formationsInput.editText?.text.toString())
                intent.putExtra(Constants.TOWNS, townsInput.editText?.text.toString())
                intent.putExtra(Constants.UNIVERSITY_TYPE, universityType)
                startActivity(intent)
            }
        return result
    }
}