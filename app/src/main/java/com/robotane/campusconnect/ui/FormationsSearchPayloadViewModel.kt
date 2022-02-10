package com.robotane.campusconnect.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotane.campusconnect.data.FiliereRepository

class FormationsSearchPayloadViewModel :ViewModel(){
}

class FormationsSearchPayloadViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormationsSearchPayloadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormationsSearchPayloadViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}