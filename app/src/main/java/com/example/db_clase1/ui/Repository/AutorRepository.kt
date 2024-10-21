package com.example.db_clase1.ui.Repository

import com.example.db_clase1.ui.DAO.AutorDao
import com.example.db_clase1.ui.Model.Autor

class AutorRepository(private val autorDao:AutorDao) {
    suspend fun insertar(autor: Autor){
        autorDao.insert(autor)
    }
    suspend fun getAllAutores(): List<Autor>{
        return autorDao.getAllAutores()
    }
    suspend fun deleteById(Idautor:Int):Int {
        return autorDao.deleteById(Idautor)
    }
    suspend fun updateAutor(autor: Autor) {
        autorDao.updateAutor(autor)
    }

}