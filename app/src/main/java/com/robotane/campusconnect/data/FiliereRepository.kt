package com.robotane.campusconnect.data

import androidx.sqlite.db.SimpleSQLiteQuery

class FiliereRepository(private val filiereDao: FiliereDao) {
    val allFilieres = filiereDao.getAllDetails()

    fun getAllFilieresByName(query: SimpleSQLiteQuery): List<FiliereOverviewModel> {
        return filiereDao.findsByName(query)
    }

    val distinctBacType = filiereDao.findDistinctBacType()
}