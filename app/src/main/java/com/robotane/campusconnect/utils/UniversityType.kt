package com.robotane.campusconnect.utils

import androidx.annotation.IdRes
import com.robotane.campusconnect.R

enum class UniversityType(@IdRes val itemId: Int) {
    ANY(R.id.fragment_search_formation_all_university_rdbtn),
    PUBLIC(R.id.fragment_search_formation_public_university_rdbtn),
    PRIVATE(R.id.fragment_search_formation_private_university_rdbtn);

    fun getDBName(): String{
        return when(itemId){
            PRIVATE.itemId -> "Privé"
            PUBLIC.itemId -> "Public"
            else -> "%"
        }
    }
    companion object {
        fun getByItemID(itemId: Int) = values().firstOrNull { it.itemId == itemId }
    }

}