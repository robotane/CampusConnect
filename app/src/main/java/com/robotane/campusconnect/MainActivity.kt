package com.robotane.campusconnect

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robotane.campusconnect.ui.FiliereApplication
import com.robotane.campusconnect.ui.FiliereListAdapter
import com.robotane.campusconnect.ui.FiliereViewModel
import com.robotane.campusconnect.ui.FiliereViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = FiliereListAdapter()
        val filiereViewModel: FiliereViewModel by viewModels {
            FiliereViewModelFactory((application as FiliereApplication).repository)
        }

        filiereViewModel.allWords.observe(this, { filieres ->
            // Update the cached copy of the words in the adapter.
            filieres?.let { adapter.submitList(it) }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}