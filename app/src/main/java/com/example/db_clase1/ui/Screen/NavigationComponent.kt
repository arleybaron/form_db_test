package com.example.db_clase1.ui.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.db_clase1.ui.Database.AutorDatabase
import com.example.db_clase1.ui.Database.LibroDatabase
import com.example.db_clase1.ui.Database.MiembroDatabase
import com.example.db_clase1.ui.Repository.AutorRepository
import com.example.db_clase1.ui.Repository.LibroRepository
import com.example.db_clase1.ui.Repository.MiembroRepository

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val autorRepository = AutorRepository(AutorDatabase.getDatabase(context).autorDao())
    val libroRepository = LibroRepository(LibroDatabase.getDatabase(context).libroDao())
    val miembroRepository = MiembroRepository(MiembroDatabase.getDatabase(context).miembroDao())

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
            MenuScreen(navController)
        }
        composable("autor") {
            ScreenAutor(navController, autorRepository)
        }
        composable("libro") {
            ScreenLibro(navController, libroRepository, autorRepository)
        }
        composable("prestamos") {
            ScreenListaPrestamo(navController)
        }
        composable("miembros") {
            ScreenMiembro(navController, miembroRepository)
        }
        composable("solicitar prestamo") {
            ScreenPrestamo(navController)
        }
    }
}