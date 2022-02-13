package com.robotane.campusconnect.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface FiliereDao {
    @Query("SELECT f.id, u.nom AS nom_universite, f.ufr, f.nom, f.series, f.place_total, f.places_restantes, f.debouches FROM filiere f JOIN universite u ON f.id_universite = u.id")
    fun getAllOverview(): Flow<List<FiliereOverviewModel>>

    @Query("SELECT u.nom AS nom_universite, f.ufr, f.nom, f.series, f.entretien, f.contraintes, f.formules_classement, f.place_total, f.places_restantes, f.conditions, f.matieres_dominantes, f.matieres_importantes_de_tl, f.niveau_sortie, f.debouches, f.informations_complementaires FROM filiere f JOIN universite u ON f.id_universite = u.id")
    fun getAllDetails(): Flow<List<FiliereDetailModel>>
//    fun getAll(): List<Filiere>

    @Query("SELECT * FROM filiere WHERE nom LIKE :name LIMIT 1")
    fun findByName(name: String): Filiere


    @RawQuery
    fun findsByName(query: SimpleSQLiteQuery): List<FiliereOverviewModel>

    @Query("SELECT DISTINCT (filiere.series) FROM filiere")
    fun findDistinctBacType(): Flow<List<String>>

}