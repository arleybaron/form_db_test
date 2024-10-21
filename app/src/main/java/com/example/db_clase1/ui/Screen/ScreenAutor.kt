package com.example.db_clase1.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.db_clase1.ui.Model.Autor
import com.example.db_clase1.ui.Repository.AutorRepository
import kotlinx.coroutines.launch

@Composable
fun ScreenAutor(navController: NavController, autorRepository: AutorRepository) {
    var autores by rememberSaveable { mutableStateOf(listOf<Autor>()) }
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var nacionalidad by rememberSaveable { mutableStateOf("") }
    var autorId by rememberSaveable { mutableStateOf<Int?>(null) } // Para almacenar el ID del autor seleccionado para editar

    val coroutineScope = rememberCoroutineScope()

    // Cargar autores al inicio
    LaunchedEffect(Unit) {
        autores = autorRepository.getAllAutores()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            // Título
            Text("Autores", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de texto para el autor
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nacionalidad,
                onValueChange = { nacionalidad = it },
                label = { Text("Nacionalidad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para agregar o actualizar un autor
            Button(
                onClick = {
                    coroutineScope.launch {
                        val nuevoAutor = Autor(IdAutor = autorId ?: 0, nombre = nombre, apellido = apellido, nacionalidad = nacionalidad)
                        if (autorId == null) {
                            autorRepository.insertar(nuevoAutor) // Agregar nuevo autor
                        } else {
                            autorRepository.updateAutor(nuevoAutor) // Actualizar autor existente
                        }
                        // Limpiar los campos de texto
                        nombre = ""
                        apellido = ""
                        nacionalidad = ""
                        autorId = null // Resetear el ID después de la operación
                        // Actualizar la lista de autores
                        autores = autorRepository.getAllAutores()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Cambiar color del botón
            ) {
                Text(if (autorId == null) "Agregar Autor" else "Actualizar Autor", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de autores
            Text("Lista de Autores:", style = MaterialTheme.typography.headlineSmall)
        }

        // Elementos en la lista de autores
        items(autores) { autor ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Nombre: ${autor.nombre}", style = MaterialTheme.typography.bodyLarge)
                        Text("Apellido: ${autor.apellido}", style = MaterialTheme.typography.bodyMedium)
                        Text("Nacionalidad: ${autor.nacionalidad}", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Icono para editar autor
                        IconButton(
                            onClick = {
                                nombre = autor.nombre
                                apellido = autor.apellido
                                nacionalidad = autor.nacionalidad
                                autorId = autor.IdAutor // Establecer el ID del autor a editar
                            }
                        ) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Editar",
                                tint = Color.Blue
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Icono para eliminar autor
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    autorRepository.deleteById(autor.IdAutor)
                                    autores = autorRepository.getAllAutores()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Eliminar",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Botón para volver al menú
            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Volver al Menú", color = Color.White)
            }
        }
    }
}
