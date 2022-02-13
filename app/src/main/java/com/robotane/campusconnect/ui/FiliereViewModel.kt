package com.robotane.campusconnect.ui

import androidx.lifecycle.*
import com.robotane.campusconnect.data.FiliereOverviewModel
import com.robotane.campusconnect.data.FiliereRepository
import com.robotane.campusconnect.utils.UniversityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import androidx.sqlite.db.SimpleSQLiteQuery




class FiliereViewModel(private val repository: FiliereRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords = repository.allFilieres.asLiveData()

    val searchFormationsLiveData = MutableLiveData<List<FiliereOverviewModel>>()

    fun fetchFormationsByQuery(
        bacType: String?,
        formations: String?,
        towns: String?,
        universityType: UniversityType?
    ) {
        var strQuery = "SELECT f.id, u.nom AS nom_universite, f.ufr, f.nom, f.series, f.place_total, f.places_restantes, f.debouches FROM filiere f JOIN universite u ON f.id_universite = u.id WHERE "
        val queryArgsList = ArrayList<String>()

        strQuery += "f.normalized_nom LIKE ? AND ("
        queryArgsList.add("%${formations}%") //TODO Normalize 'formation' as we are searching in a normalized column

        bacType?.split(Pattern.compile("[, \t]"))?.forEach {
            if (it.isNotBlank()) {
                strQuery += "f.series LIKE ? OR "
                queryArgsList.add("%${it}%")
            }
        }
        strQuery = strQuery.removeSuffix(" OR ") + ") "

        strQuery += "AND ("

        towns?.split(Pattern.compile("[, \t]"))?.forEach {
            if (it.isNotBlank()) {
                strQuery += "u.normalized_ville LIKE ? OR "
                queryArgsList.add("%${it}%")  //TODO Also normalize 'it'
            }
        }
        strQuery = strQuery.removeSuffix(" OR ") + ") "
        strQuery += "AND u.status LIKE ?"
        queryArgsList.add("%${universityType?.getDBName()}%")
        strQuery = strQuery.replace("AND () ", "")
        println(strQuery)
        val query = SimpleSQLiteQuery(strQuery, queryArgsList.toArray())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val findFormations = repository.getAllFilieresByName(query)
                searchFormationsLiveData.postValue(findFormations)
            } catch (e: Exception) {
                TODO("update the loading state")
            }
        }
    }
}

class FiliereViewModelFactory(private val repository: FiliereRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FiliereViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FiliereViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}