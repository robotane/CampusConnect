package com.robotane.campusconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.robotane.campusconnect.fragments.FormationDetailsFragment

class FormationDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_formation_details)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_formation_details_fragment_container, FormationDetailsFragment.newInstance())
                .commitNow()
        }
    }
}