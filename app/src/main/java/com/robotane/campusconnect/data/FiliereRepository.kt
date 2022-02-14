package com.robotane.campusconnect.data

import androidx.sqlite.db.SimpleSQLiteQuery

class FiliereRepository(private val filiereDao: FiliereDao) {
    val allFilieres = filiereDao.getFormationsDetails()

    fun findsFormationsOverview(query: SimpleSQLiteQuery): List<FiliereOverviewModel> {
        return filiereDao.findsFormationsOverview(query)
    }

    val distinctBacType = filiereDao.findDistinctBacType()
    val findDistinctTowns = filiereDao.findDistinctTowns()
}