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


class FormationsResultViewModel(private val repository: FiliereRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val searchFormationsLiveData = MutableLiveData<List<FiliereOverviewModel>>()
    var bacType = ""
    var formations = ""
    var towns = ""
    var universityType = ""
    var isTwoPane = false

    val TAG = javaClass.simpleName

    fun fetchFormationsByQuery(
        bacType: String?,
        formations: String?,
        towns: String?,
        universityType: UniversityType?
    ) {
        setQueryProperties(bacType, formations, towns, universityType)
        //TODO add alias queries:
        // droit -> sciences juridiques or droit
        // biologie -> biologique or biologie
        // and so on...
        println("Fetching")
        var query =
            "SELECT f.id, u.nom AS nom_universite, u.ville, f.ufr, f.nom, f.conditions FROM filiere f JOIN universite u ON f.id_universite = u.id WHERE "
        val queryArgsList = ArrayList<String>()

        //TODO Next goal is testing

        // Name query builder
        query += "("
        stripAccents(this.formations)
            .lowercase().split(Regex(", "))
            .forEach { form ->
                query += "("
                form.split(Regex("[ \t\r]")).forEach {
                    if (it.isNotBlank()) {
                        query += "(f.normalized_nom LIKE ? OR f.sigle_nom LIKE ?) AND "
                        queryArgsList.addAll(listOf("%${it.trim()}%", it.trim()))
                    }
                }
                query = query.removeSuffix(" AND ") + ") "
                if (query.endsWith("() ")) {
                    query = query.removeSuffix("() ")
                } else
                    query += "OR "
            }
        query = query.removeSuffix(" OR ") + ") "
        if (query.endsWith("() ")) {
            query = query.removeSuffix("() ")
        } else
            query += "AND "

        // Series query builder
        query += "("
        this.bacType
            .split(Regex(", "))
            .forEach {
                if (it.isNotBlank()) {
                    query += "' '||REPLACE(f.series, ',', '')||' ' LIKE ? OR "
                    queryArgsList.addAll(listOf("% $it %"))
                }
            }
        query = query.removeSuffix(" OR ") + ") "
        if (query.endsWith("() ")) {
            query = query.removeSuffix("() ")
        } else
            query += "AND "

        // Cities query builder
        query += "("
        stripAccents(this.towns)
            .lowercase().split(Regex(", "))
            .forEach {
                if (it.isNotBlank()) {
                    query += "u.normalized_ville LIKE ? OR "
                    queryArgsList.add("%$it%")
                }
            }
        query = query.removeSuffix(" OR ") + ") "
        if (query.endsWith("() ")) {
            query = query.removeSuffix("() ")
        } else
            query += "AND "

        // Status query builder
        query += "u.status LIKE ?"
        queryArgsList.add(this.universityType)

        Log.d(TAG, "formations query str: $query")
        Log.d(TAG, "formations query args: ${queryArgsList.toList()}")
        val simpleSQLiteQuery = SimpleSQLiteQuery(query, queryArgsList.toArray())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val findFormations = repository.findsFormationsOverview(simpleSQLiteQuery)
                searchFormationsLiveData.postValue(findFormations)
            } catch (e: Exception) {
                TODO("update the loading state")
            }
        }
    }

    private fun setQueryProperties(
        bacType: String?,
        formations: String?,
        towns: String?,
        universityType: UniversityType?
    ) {
        this.bacType = bacType?.let { getCleanQueryString(it) } ?: ""
        this.formations = formations?.let { getCleanQueryString(it) } ?: ""
        this.towns = towns?.let { getCleanQueryString(it) } ?: ""
        this.universityType = universityType?.getDBName() ?: "%"
    }

    private fun getCleanQueryString(queryString: String) = queryString.split(",")
        .map(String::trim)
        .toSet()
        .filter(String::isNotBlank)
        .joinToString(", ")
        .apply { println("'$this'") }
}

class FormationsResultViewModelFactory(private val repository: FiliereRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormationsResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormationsResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}