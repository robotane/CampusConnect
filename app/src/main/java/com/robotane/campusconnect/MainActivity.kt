package com.robotane.campusconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.robotane.campusconnect.fragments.SearchFormationsFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFormationsFragment.newInstance())
                .commitNow()
        }
    }
}