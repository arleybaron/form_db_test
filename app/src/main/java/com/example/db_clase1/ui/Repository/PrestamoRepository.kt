package com.example.db_clase1.ui.Repository

import com.example.db_clase1.ui.DAO.PrestamoDao
import com.example.db_clase1.ui.Model.Prestamo

class PrestamoRepository(private val prestamoDao:PrestamoDao) {
    suspend fun insertar(prestamo: Prestamo){
        prestamoDao.insert(prestamo)
    }
    suspend fun getAllPrestamos(): List<Prestamo>{
        return prestamoDao.getAllPrestamos()
    }
    suspend fun deleteById(Idprestamo:Int):Int {
        return prestamoDao.deleteById(Idprestamo)
    }
    suspend fun updatePrestamo(prestamo: Prestamo) {
        prestamoDao.updatePrestamo(prestamo)
    }
}