package com.example.db_clase1.ui.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.db_clase1.ui.DAO.MiembroDao
import com.example.db_clase1.ui.Model.Miembro

//Abstract es usado para evitar crear nuevas instancias de la BD y room gestiona la relacion
@Database(entities = [Miembro::class], version = 1, exportSchema = false)
abstract class MiembroDatabase: RoomDatabase() {
    abstract fun miembroDao(): MiembroDao

    companion object{
        @Volatile
        private  var INSTANCE: MiembroDatabase? = null

        // Permitir crear una instancia en la BD
        fun getDatabase(context: Context): MiembroDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MiembroDatabase::class.java,
                    "miembrodatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}