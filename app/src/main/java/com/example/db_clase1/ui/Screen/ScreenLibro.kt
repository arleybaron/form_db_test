package com.example.db_clase1.ui.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.db_clase1.ui.Model.Autor
import com.example.db_clase1.ui.Model.Libro
import com.example.db_clase1.ui.Repository.AutorRepository
import com.example.db_clase1.ui.Repository.LibroRepository
import kotlinx.coroutines.launch

@Composable
fun ScreenLibro(
    navController: NavController,
    libroRepository: LibroRepository,
    autorRepository: AutorRepository
) {
    var libros by remember { mutableStateOf(listOf<Libro>()) }
    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var selectedAutor by remember { mutableStateOf<Autor?>(null) }
    val autores = remember { mutableStateListOf<Autor>() }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Cargar los autores al inicio
    LaunchedEffect(Unit) {
        autores.addAll(autorRepository.getAllAutores()) // Llenar la lista de autores
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Gestión de Libros", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el título
        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para el género
        TextField(
            value = genero,
            onValueChange = { genero = it },
            label = { Text("Género") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Desplegable para seleccionar autor
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { isDropdownExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedAutor?.let { "${it.nombre} ${it.apellido}" } ?: "Seleccionar Autor")
            }

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                autores.forEach { autor ->
                    DropdownMenuItem(
                        text = { Text(text = "${autor.nombre} ${autor.apellido}")},
                        onClick = {
                            selectedAutor = autor
                            isDropdownExpanded = false
                        })
                    }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar libro
        Button(
            onClick = {
                coroutineScope.launch {
                    if (selectedAutor != null) {
                        val nuevoLibro = Libro(
                            titulo = titulo,
                            genero = genero,
                            IdAutor = selectedAutor!!.IdAutor,
                            nombreAutor = "${selectedAutor!!.nombre} ${selectedAutor!!.apellido}"
                        )
                        libroRepository.insertar(nuevoLibro)
                        // Limpiar campos
                        titulo = ""
                        genero = ""
                        selectedAutor = null
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Libro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de libros
        Text("Lista de Libros:", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(libros) { libro ->
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
                            Text("Título: ${libro.titulo}", style = MaterialTheme.typography.bodyLarge)
                            Text("Género: ${libro.genero}", style = MaterialTheme.typography.bodyMedium)
                            Text("Autor: ${libro.nombreAutor}", style = MaterialTheme.typography.bodyMedium)
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Icono para editar libro
                            IconButton(
                                onClick = {
                                    // Lógica para editar libro
                                    // Puedes establecer valores en los campos para editar
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "Editar",
                                    tint = Color.Blue
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Icono para eliminar libro
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        libroRepository.deleteById(libro.IdLibro) // Asegúrate de que IdLibro es correcto
                                        libros = libroRepository.getAllLibros() // Actualizar lista de libros
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
        }

        Spacer(modifier = Modifier.height(16.dp))

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
