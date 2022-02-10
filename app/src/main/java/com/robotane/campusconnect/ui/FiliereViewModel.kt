package com.robotane.campusconnect.ui

import androidx.lifecycle.*
import com.robotane.campusconnect.data.Filiere
import com.robotane.campusconnect.data.FiliereRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FiliereViewModel(private val repository: FiliereRepository):ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords = repository.allFilieres.asLiveData()

    val searchFormationsLiveData = MutableLiveData<List<Filiere>>()

    fun fetchFormationsByQuery(query: String) {
        // optional: add wildcards to the filter
    val f = when {
        query.isEmpty() -> "%"
        else -> "%$query%"
    }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                /*//1
                withContext(Dispatchers.Main) {
                    movieLoadingStateLiveData.value = MovieLoadingState.LOADING
                }*/

                val movies = repository.getAllFilieresByName(f)
                searchFormationsLiveData.postValue(movies)
/*
                //2
                movieLoadingStateLiveData.postValue(MovieLoadingState.LOADED)*/
            } catch (e: Exception) {
                TODO("update the loading state")
      /*          //3
                movieLoadingStateLiveData.postValue(MovieLoadingState.INVALID_API_KEY)*/
            }
        }
    }

}

class FiliereViewModelFactory(private val repository: FiliereRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FiliereViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FiliereViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}