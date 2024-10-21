package com.example.db_clase1.ui.Screen

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.db_clase1.ui.Model.Miembro
import com.example.db_clase1.ui.Repository.MiembroRepository
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Composable
fun ScreenMiembro(navController: NavController, miembroRepository: MiembroRepository) {
    var miembros = remember { mutableStateListOf<Miembro>() }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember{ mutableStateOf("") }
    var fechaIngreso by remember{ mutableStateOf(LocalDate.now()) }
    var miembroId by remember{ mutableStateOf<Int?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Cargar los miembros al inicio
    LaunchedEffect(Unit) {
        val listaMiembros = miembroRepository.getAllMiembros()
        miembros.clear()
        miembros.addAll(listaMiembros)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text("Miembros", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para el nombre
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para el apellido
            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para seleccionar fecha de ingreso
            Button(onClick = {
                val date = Calendar.getInstance()
                date.set(fechaIngreso.year, fechaIngreso.monthValue - 1, fechaIngreso.dayOfMonth)
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        fechaIngreso = LocalDate.of(year, month + 1, dayOfMonth)
                    },
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    date.set(2024, 9, 10)
                    datePicker.minDate = date.timeInMillis
                }.show()
            }) {
                Text("Seleccionar Fecha de Ingreso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Fecha seleccionada: $fechaIngreso")

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para agregar o actualizar miembro
            Button(
                onClick = {
                    coroutineScope.launch {
                        val timestamp = fechaIngreso.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        val nuevoMiembro = Miembro(
                            IdMiembro = miembroId ?: 0,
                            nombre = nombre,
                            apellido = apellido,
                            fechaincripcion = timestamp
                        )
                        if (miembroId == null) {
                            miembroRepository.insertar(nuevoMiembro)
                        } else {
                            miembroRepository.updateMiembro(nuevoMiembro)
                        }
                        nombre = ""
                        apellido = ""
                        fechaIngreso = LocalDate.now()
                        miembroId = null
                        miembros.clear()
                        miembros.addAll(miembroRepository.getAllMiembros())
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (miembroId == null) "Agregar Miembro" else "Actualizar Miembro")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(miembros.size) { index ->
            val miembro = miembros[index]
            // Aquí ya puedes procesar el miembro
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val fechaIngresoLocalDate =
                        Instant.ofEpochMilli(miembro.fechaincripcion).atZone(ZoneId.systemDefault()).toLocalDate()
                    Text("Nombre: ${miembro.nombre} ${miembro.apellido}", style = MaterialTheme.typography.bodyLarge)
                    Text("Fecha de Ingreso: $fechaIngresoLocalDate", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // Botón para editar miembro (icono)
                        IconButton(onClick = {
                            nombre = miembro.nombre
                            apellido = miembro.apellido
                            fechaIngreso = Instant.ofEpochMilli(miembro.fechaincripcion)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            miembroId = miembro.IdMiembro
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar Miembro",
                                tint = Color.Blue
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Botón para eliminar miembro (icono)
                        IconButton(onClick = {
                            coroutineScope.launch {
                                miembroRepository.deleteById(miembro.IdMiembro)
                                miembros.clear()
                                miembros.addAll(miembroRepository.getAllMiembros())
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar Miembro",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }


        item {
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { navController.navigate("menu") }) {
                Text("Volver al Menú")
            }
        }
    }
}
