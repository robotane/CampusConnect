package com.robotane.campusconnect.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Filiere::class], version = 1)
abstract class CampusRoomData : RoomDatabase() {
    abstract fun filiereDao(): FiliereDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CampusRoomData? = null

        fun getDatabase(context: Context): CampusRoomData {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CampusRoomData::class.java,
                    "campus.db"
                ).createFromAsset("db_campus_faso.db")
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}