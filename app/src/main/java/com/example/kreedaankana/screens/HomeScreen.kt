package com.example.kreedaankana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4E2))
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Welcome to Kreeda-Ankana",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF0B6E2E)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Booking Screen
        Button(
            onClick = {
                navController.navigate("booking")
            },
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.SportsCricket,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Book Ground Slot")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Challenge Board
        Button(
            onClick = {
                navController.navigate("challenge")
            },
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.SportsKabaddi,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Challenge Board")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Score Wall
        Button(
            onClick = {
                navController.navigate("score")
            },
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Scoreboard,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Score Wall")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ground Calendar
        Button(
            onClick = {
                navController.navigate("calendar")
            },
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Ground Calendar")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Logout Button
        OutlinedButton(
            onClick = {

                FirebaseAuth.getInstance().signOut()

                navController.navigate("login") {

                    popUpTo("home") {
                        inclusive = true
                    }
                }
            },
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Logout")
            }
        }
    }
}