package com.robotane.campusconnect.ui

import android.app.Application
import com.robotane.campusconnect.data.CampusRoomData
import com.robotane.campusconnect.data.FiliereRepository

class FiliereApplication : Application() {
    val database by lazy { CampusRoomData.getDatabase(this) }
    val repository by lazy { FiliereRepository(database.filiereDao()) }
}