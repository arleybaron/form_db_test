package com.example.db_clase1.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScreenListaPrestamo(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Lista de Préstamos", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = { navController.navigate("menu") }) {
            Text("Volver al Menú")
        }
    }
}