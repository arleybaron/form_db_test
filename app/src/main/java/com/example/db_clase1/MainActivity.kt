package com.example.db_clase1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.db_clase1.DAO.UserDao
import com.example.db_clase1.Database.UserDatabase
import com.example.db_clase1.Repository.UserRepository
import com.example.db_clase1.Screen.UserApp
import com.example.db_clase1.ui.theme.Db_clase1Theme

class MainActivity : ComponentActivity() {

    private  lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = UserDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userRepository = UserRepository(userDao) // Inicializa el Repositorio

        enableEdgeToEdge()
        setContent {
            UserApp(userRepository)
        }
    }
}