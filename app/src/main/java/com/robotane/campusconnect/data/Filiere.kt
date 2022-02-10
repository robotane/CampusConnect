package com.robotane.campusconnect.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filiere")
data class Filiere(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "nom_universite") val nomUniversite: String?,
    @ColumnInfo(name = "ufr") val ufr: String?,
    @ColumnInfo(name = "nom") val nom: String?,
    @ColumnInfo(name = "series") val series: String?,
    @ColumnInfo(name = "entretien") val entretien: String?,
    @ColumnInfo(name = "contraintes") val contraintes: String?,
    @ColumnInfo(name = "formules_classement") val formulesClassement: String?,
    @ColumnInfo(name = "place_total") val placeTotal: Int?,
    @ColumnInfo(name = "places_restantes") val placesRestantes: Int?,
    @ColumnInfo(name = "conditions") val conditions: String?,
    @ColumnInfo(name = "matieres_dominantes") val matieresDominantes: String?,
    @ColumnInfo(name = "matieres_importantes_de_tl") val matieresImportantesDeTl: String?,
    @ColumnInfo(name = "niveau_sortie") val niveauSortie: String?,
    @ColumnInfo(name = "debouches") val debouches: String?,
    @ColumnInfo(name = "informations_complementaires") val informationsComplementaires: String?
)
