package com.robotane.campusconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.robotane.campusconnect.fragments.FormationsResultFragment

class FormationsResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_formations_result)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_formation_result_fragment_container, FormationsResultFragment.newInstance(intent.extras))
                .commitNow()
        }
    }
}