package com.example.db_clase1.ui.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Libros" )
data class Libro (
    @PrimaryKey(autoGenerate = true)
    val IdLibro: Int = 0
    ,val titulo: String
    ,val genero: String
    ,val IdAutor: Int
    , val nombreAutor: String? = null
)