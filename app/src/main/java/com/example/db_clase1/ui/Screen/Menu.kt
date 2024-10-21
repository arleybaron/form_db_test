package com.example.db_clase1.ui.Screen

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Librería Room", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("autor") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Cambia el color aquí
        ) {
            Icon(Icons.Filled.Person, contentDescription = "Autores")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Autores")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("libro") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5)) // Cambia el color aquí
        ) {
            Icon(Icons.Filled.AccountBox, contentDescription = "Libros")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Libros")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("prestamos") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)) // Cambia el color aquí
        ) {
            Icon(Icons.Filled.Favorite, contentDescription = "Lista de Préstamos")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Lista de Préstamos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("miembros") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC)) // Cambia el color aquí
        ) {
            Icon(Icons.Filled.Face, contentDescription = "Miembros")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Miembros")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("solicitar prestamo") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5)) // Cambia el color aquí
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Préstamos")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Préstamos")
        }
    }
}
