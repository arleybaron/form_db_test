package com.example.db_clase1.ui.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.db_clase1.ui.Model.Libro

@Dao
interface LibrosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Revisión de conflictos entre registros
    suspend fun insert (libro: Libro)

    //@Query("SELECT l.IdLibro, l.titulo, l.genero, a.nombre AS nombreAutor FROM Libros AS l INNER JOIN Autores AS a ON a.IdAutor = l.IdAutor")
    @Query("SELECT * FROM Libros")
    suspend fun getAllLibros(): List<Libro>

    @Query("DELETE FROM Libros WHERE IDLibro = :Idlibro")
    suspend fun deleteById(Idlibro: Int):Int

    @Update
    suspend fun updateLibro(libro: Libro)
}