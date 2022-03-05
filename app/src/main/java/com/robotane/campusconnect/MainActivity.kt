package com.robotane.campusconnect

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.robotane.campusconnect.fragments.SearchFormationsFragment
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.UniversityType


class MainActivity : AppCompatActivity(), SearchFormationsFragment.OnButtonClickedListener {
    private lateinit var searchFormationsFragment: SearchFormationsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            searchFormationsFragment = SearchFormationsFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_search_formations_fragment_container, searchFormationsFragment)
                .commitNow()
        }
        else{
            searchFormationsFragment = supportFragmentManager.findFragmentById(R.id.activity_search_formations_fragment_container) as SearchFormationsFragment
        }
    }

    override fun onButtonClicked(view: View?) {
        // Starts a FormationResultActivity with the values typed as search queries
        val extras = Bundle()
        extras.putString(Constants.BAC_TYPE, searchFormationsFragment.viewModel.bacType.value)
        extras.putString(Constants.FORMATIONS, searchFormationsFragment.viewModel.formation.value)
        extras.putString(Constants.TOWNS, searchFormationsFragment.viewModel.towns.value)
        extras.putSerializable(
            Constants.UNIVERSITY_TYPE,
            searchFormationsFragment.viewModel.universityType.value?.let { UniversityType.getByItemID(it) })
        val intent = Intent(view?.context, FormationsResultActivity::class.java)
        intent.putExtras(extras)
        startActivity(intent)
    }
}