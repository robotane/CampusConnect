package com.robotane.campusconnect.data

class FiliereRepository(private val filiereDao: FiliereDao) {
    val allFilieres = filiereDao.getAll()
}