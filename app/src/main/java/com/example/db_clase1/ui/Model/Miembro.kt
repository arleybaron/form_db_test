package com.example.db_clase1.ui.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Miembros" )
data class Miembro (
    @PrimaryKey(autoGenerate = true)
    val IdMiembro: Int = 0
    ,val nombre: String
    ,val apellido: String
    ,val fechaincripcion: Long // Cambiado a Long para almacenar un timestamp
)