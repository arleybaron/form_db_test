package com.example.db_clase1.ui.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.db_clase1.ui.DAO.LibrosDao
import com.example.db_clase1.ui.Model.Libro

//Abstract es usado para evitar crear nuevas instancias de la BD y room gestiona la relacion
@Database(entities = [Libro::class], version = 1, exportSchema = false)
abstract class LibroDatabase:RoomDatabase() {
    abstract fun libroDao(): LibrosDao

    companion object {
        @Volatile
        private var INSTANCE: LibroDatabase? = null

        // Permitir crear una instancia en la BD
        fun getDatabase(context: Context): LibroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibroDatabase::class.java,
                    "librodatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}