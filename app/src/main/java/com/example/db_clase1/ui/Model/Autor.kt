package com.example.db_clase1.ui.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Autores" )
data class Autor (
    @PrimaryKey(autoGenerate = true)
    val IdAutor: Int = 0
    ,val nombre: String
    ,val apellido: String
    ,val nacionalidad: String
){
}