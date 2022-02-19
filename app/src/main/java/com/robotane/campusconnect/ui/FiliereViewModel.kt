package com.robotane.campusconnect.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.robotane.campusconnect.data.FiliereOverviewModel
import com.robotane.campusconnect.data.FiliereRepository
import com.robotane.campusconnect.utils.HelperFunctions.Companion.stripAccents
import com.robotane.campusconnect.utils.UniversityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class FiliereViewModel(private val repository: FiliereRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val searchFormationsLiveData = MutableLiveData<List<FiliereOverviewModel>>()

    val TAG = javaClass.simpleName

    fun fetchFormationsByQuery(
        bacType: String?,
        formations: String?,
        towns: String?,
        universityType: UniversityType?
    ) {
        var strQuery = "SELECT f.id, u.nom AS nom_universite, u.ville, f.ufr, f.nom, f.conditions FROM filiere f JOIN universite u ON f.id_universite = u.id WHERE "
        val queryArgsList = ArrayList<String>()

        strQuery += "("
        formations?.let { NNFormations ->
            stripAccents(NNFormations).lowercase().split(Pattern.compile("[, \t]")).forEach {
                if (it.isNotBlank()) {
                    strQuery += "f.normalized_nom LIKE ? OR "
                    queryArgsList.add("%${it}%")
                }
            }
        }
        strQuery = strQuery.removeSuffix(" OR ") + ") "
        if (strQuery.endsWith("() ")) {
            strQuery = strQuery.removeSuffix("() ")
        }
        else
            strQuery += "AND "

        strQuery += "("
        bacType?.split(Pattern.compile("[, \t]"))?.forEach {
            if (it.isNotBlank()) {
                strQuery += "f.series LIKE ? OR "
                queryArgsList.add("%${it}%")
            }
        }
        strQuery = strQuery.removeSuffix(" OR ") + ") "
        if (strQuery.endsWith("() ")) {
            strQuery = strQuery.removeSuffix("() ")
        }else
            strQuery += "AND "

        strQuery += "("
        towns?.let {NNTowns ->
            stripAccents(NNTowns).lowercase().split(",").forEach {
                if (it.isNotBlank()) {
                    strQuery += "u.normalized_ville LIKE ? OR "
                    queryArgsList.add("%${it.trim()}%")
                }
            }
        }
        strQuery = strQuery.removeSuffix(" OR ") + ") "
        if (strQuery.endsWith("() ")) {
            strQuery = strQuery.removeSuffix("() ")
        }
        else
             strQuery += "AND "
        strQuery += "u.status LIKE ?"
        queryArgsList.add("${universityType?.getDBName()}")

        Log.d(TAG, "formations query str: $strQuery")
        Log.d(TAG, "formations query args: ${queryArgsList.toList()}")
        val query = SimpleSQLiteQuery(strQuery, queryArgsList.toArray())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val findFormations = repository.findsFormationsOverview(query)
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