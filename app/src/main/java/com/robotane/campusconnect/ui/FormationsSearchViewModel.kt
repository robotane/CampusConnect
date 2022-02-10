package com.robotane.campusconnect.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.robotane.campusconnect.FormationsResultActivity
import com.robotane.campusconnect.utils.Constants
import com.robotane.campusconnect.utils.UniversityType

class FormationsSearchViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    // The ViewModel properties observe directly the values stored in the StateHandler, so, changes are bidirectional
    val universityType =
        savedStateHandle.getLiveData(Constants.UNIVERSITY_TYPE, UniversityType.ANY.itemId)
    val formation = savedStateHandle.getLiveData(Constants.FORMATIONS, "")
    val towns = savedStateHandle.getLiveData(Constants.TOWNS, "")
    val bacType = savedStateHandle.getLiveData(Constants.BAC_TYPE, "")

    // Wonder if I should move this listener in to the fragment as it deals with context
    fun onSearchButtonClick(view: View) {
        // Starts a FormationResultActivity with the values typed as search queries
        val intent = Intent(view.context, FormationsResultActivity::class.java)
        intent.putExtra(Constants.BAC_TYPE, bacType.value)
        intent.putExtra(Constants.FORMATIONS, formation.value)
        intent.putExtra(Constants.TOWNS, towns.value)
        intent.putExtra(Constants.UNIVERSITY_TYPE,
            universityType.value?.let { UniversityType.getByItemID(it) })
        view.context.startActivity(intent)
    }
}

class FormationsSearchViewModelFactory(owner: SavedStateRegistryOwner, defaultArgs: Bundle?) :
    AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(FormationsSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormationsSearchViewModel(handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}