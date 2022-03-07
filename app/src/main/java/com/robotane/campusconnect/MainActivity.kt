package com.robotane.campusconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.robotane.campusconnect.fragments.FormationsResultFragment
import com.robotane.campusconnect.fragments.SearchFormationsFragment
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.UniversityType


class MainActivity : AppCompatActivity(), SearchFormationsFragment.OnButtonClickedListener {
    private lateinit var searchFormationsFragment: SearchFormationsFragment
    private var formationsResultFragment: FormationsResultFragment? = null
    private var isTwoPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            searchFormationsFragment = SearchFormationsFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.activity_main_search_formations_fragment_container,
                    searchFormationsFragment
                )
                .commitNow()
        } else {
            searchFormationsFragment =
                supportFragmentManager.findFragmentById(R.id.activity_main_search_formations_fragment_container) as SearchFormationsFragment
        }
        isTwoPane =
            findViewById<View?>(R.id.activity_main_formations_result_fragment_container) != null
        configureAndShowResultFragment()
    }

    private fun configureAndShowResultFragment() {
        formationsResultFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_formations_result_fragment_container) as FormationsResultFragment?

        // We only add formationsResultFragment in Tablet mode (If found activity_formation_result_fragment_container)
        if (formationsResultFragment == null && isTwoPane) {
            formationsResultFragment = FormationsResultFragment.newInstance(Bundle())
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.activity_main_formations_result_fragment_container,
                    formationsResultFragment!!
                )
                .commitNow()
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
            searchFormationsFragment.viewModel.universityType.value?.let(UniversityType.Companion::getByItemID)
        )
        extras.putBoolean(Constants.IS_TWO_PANE, isTwoPane)
        if (isTwoPane) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.activity_main_formations_result_fragment_container,
                    FormationsResultFragment.newInstance(extras)
                )
                .commitNow()
        } else {
            val intent = Intent(view?.context, FormationsResultActivity::class.java)
            intent.putExtras(extras)
            startActivity(intent)
        }
    }
}