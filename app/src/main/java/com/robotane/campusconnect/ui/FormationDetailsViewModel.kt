package com.robotane.campusconnect.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.robotane.campusconnect.data.FiliereDetailModel
import com.robotane.campusconnect.data.FiliereRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormationDetailsViewModel (private val repository: FiliereRepository) :ViewModel() {
    val formation = MutableLiveData<FiliereDetailModel>()
    fun getFormationDetails(formationId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            formation.postValue(repository.getFormationDetails(formationId))
        }
    }
}

class FormationsDetailsViewModelFactory(private val repository: FiliereRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormationDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormationDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}