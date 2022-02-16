package com.robotane.campusconnect.data

import androidx.sqlite.db.SimpleSQLiteQuery

class FiliereRepository(private val filiereDao: FiliereDao) {
    fun getFormationDetails(id: Int): FiliereDetailModel {
        return filiereDao.getFormationDetails(id)
    }

    fun findsFormationsOverview(query: SimpleSQLiteQuery): List<FiliereOverviewModel> {
        return filiereDao.findsFormationsOverview(query)
    }

    val distinctBacType = filiereDao.findDistinctBacType()
    val findDistinctTowns = filiereDao.findDistinctTowns()
}