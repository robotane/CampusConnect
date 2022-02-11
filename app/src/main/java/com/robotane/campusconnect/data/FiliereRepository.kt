package com.robotane.campusconnect.data

import kotlinx.coroutines.flow.Flow

class FiliereRepository(private val filiereDao: FiliereDao) {
    val allFilieres = filiereDao.getAll()

    fun getAllFilieresByName(name: String): List<Filiere> {
        return filiereDao.findsByName(name)
    }

    val distinctBacType = filiereDao.findDistinctBacType()
}