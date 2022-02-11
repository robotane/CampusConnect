package com.robotane.campusconnect.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FiliereDao {
    @Query("SELECT * FROM filiere")
    fun getAll(): Flow<List<Filiere>>
//    fun getAll(): List<Filiere>

    @Query("SELECT * FROM filiere WHERE nom LIKE :name LIMIT 1")
    fun findByName(name: String): Filiere

    @Query("SELECT * FROM filiere WHERE nom LIKE :name")
    fun findsByName(name: String): List<Filiere>

    @Query("SELECT DISTINCT (filiere.series) FROM filiere")
    fun findDistinctBacType(): Flow<List<String>>

}