package com.example.db_clase1.ui.Repository

import com.example.db_clase1.ui.DAO.MiembroDao
import com.example.db_clase1.ui.Model.Autor
import com.example.db_clase1.ui.Model.Miembro

class MiembroRepository(private val miembroDao:MiembroDao) {
    suspend fun insertar(miembro: Miembro){
        miembroDao.insert(miembro)
    }
    suspend fun getAllMiembros(): List<Miembro>{
        return miembroDao.getAllMiembros()
    }
    suspend fun deleteById(Idmiembro:Int):Int {
        return miembroDao.deleteById(Idmiembro)
    }
    suspend fun updateMiembro(miembro: Miembro) {
        miembroDao.updateMiembro(miembro)
    }
}