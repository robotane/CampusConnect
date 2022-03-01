package com.robotane.campusconnect.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

data class FiliereOverviewModel(
    val id: Int?,
    @ColumnInfo(name = "nom_universite") val nomUniversite: String?,
    val ufr: String?,
    val nom: String?,
    val conditions: String?,
    val ville: String?,
)

data class UniversiteDetailModel(
    val nom: String?,
    val ville: String?,
    val status: String?,
    val contacts: String?,
    @ColumnInfo(name = "autres_details") val autresDetails: String?,
)

data class FiliereDetailModel(
    @ColumnInfo(name = "nom_universite") val nomUniversite: String?,
    val ufr: String?,
    val nom: String?,
    val series: String?,
    val entretien: String?,
    val contraintes: String?,
    @ColumnInfo(name = "formules_classement") val formulesClassement: String?,
    @ColumnInfo(name = "places_total") val placeTotal: Int?,
    @ColumnInfo(name = "places_restantes") val placesRestantes: Int?,
    val conditions: String?,
    @ColumnInfo(name = "matieres_dominantes") val matieresDominantes: String?,
    @ColumnInfo(name = "matieres_importantes_de_tl") val matieresImportantesDeTl: String?,
    @ColumnInfo(name = "niveau_sortie") val niveauSortie: String?,
    val debouches: String?,
    @ColumnInfo(name = "informations_complementaires") val informationsComplementaires: String?
)

@Entity(tableName = "filiere")
data class Filiere(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "id_universite") val idUniversite: Int?,
    @ColumnInfo(name = "ufr") val ufr: String?,
    @ColumnInfo(name = "nom") val nom: String?,
    @ColumnInfo(name = "normalized_nom") val normalizedNom: String?,
    @ColumnInfo(name = "sigle_nom") val sigleNom: String?,
    @ColumnInfo(name = "series") val series: String?,
    @ColumnInfo(name = "entretien") val entretien: String?,
    @ColumnInfo(name = "contraintes") val contraintes: String?,
    @ColumnInfo(name = "formules_classement") val formulesClassement: String?,
    @ColumnInfo(name = "places_total") val placeTotal: Int?,
    @ColumnInfo(name = "places_restantes") val placesRestantes: Int?,
    @ColumnInfo(name = "conditions") val conditions: String?,
    @ColumnInfo(name = "matieres_dominantes") val matieresDominantes: String?,
    @ColumnInfo(name = "matieres_importantes_de_tl") val matieresImportantesDeTl: String?,
    @ColumnInfo(name = "niveau_sortie") val niveauSortie: String?,
    @ColumnInfo(name = "debouches") val debouches: String?,
    @ColumnInfo(name = "informations_complementaires") val informationsComplementaires: String?
)

@Entity(tableName = "universite")
data class Universite(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "nom") val nom: String?,
    @ColumnInfo(name = "normalized_nom") val normalizedNom: String?,
    @ColumnInfo(name = "ville") val ville: String?,
    @ColumnInfo(name = "normalized_ville") val normalizedVille: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "contacts") val contacts: String?,
    @ColumnInfo(name = "autres_details") val autresDetails: String?,
)

@Entity(tableName = "filiere_fts")
@Fts4(contentEntity = Filiere::class)
data class FiliereFTS(
    @ColumnInfo(name = "nom")
    val nom: String?
)
