package com.example.kreedaankana.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kreedaankana.R
import com.google.firebase.auth.FirebaseAuth

data class DashboardItem(
    val title: String
)

@Composable
fun DashboardScreen(navController: NavController) {

    val dashboardItems = listOf(
        DashboardItem("Book Ground"),
        DashboardItem("Challenge Board"),
        DashboardItem("Score Wall"),
        DashboardItem("Ground Calendar")
    )

    Scaffold(

        // 🔻 BOTTOM NAVIGATION
        bottomBar = {

            NavigationBar(
                containerColor = Color(0xFF2E7D32)
            ) {

                // HOME
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("home")
                    },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Home")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White,
                        indicatorColor = Color(0xFF1B5E20)
                    )
                )

                // BOOKING
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("booking")
                    },
                    icon = {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Booking")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White,
                        indicatorColor = Color(0xFF1B5E20)
                    )
                )

                // PROFILE
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("profile")
                        // Add Profile Screen Later
                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Profile")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White,
                        indicatorColor = Color(0xFF1B5E20)
                    )
                )

                // LOGOUT
                NavigationBarItem(
                    selected = false,
                    onClick = {

                        FirebaseAuth.getInstance().signOut()

                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Logout")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White,
                        indicatorColor = Color(0xFF1B5E20)
                    )
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F8E9))
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            // APP TITLE
            Text(
                text = "Kreeda-Ankana",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Village Sports Organizer",
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            // SPORTS BANNER IMAGE
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.sports_banner),
                contentDescription = "Sports Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome Player 👋",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // DASHBOARD GRID
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),

                verticalArrangement = Arrangement.spacedBy(16.dp),

                horizontalArrangement = Arrangement.spacedBy(16.dp),

                modifier = Modifier.fillMaxSize()
            ) {

                items(dashboardItems) { item ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .clickable {

                                when (item.title) {

                                    "Book Ground" -> {
                                        navController.navigate("booking")
                                    }

                                    "Challenge Board" -> {
                                        navController.navigate("challenge")
                                    }

                                    "Score Wall" -> {
                                        navController.navigate("score")
                                    }

                                    "Ground Calendar" -> {
                                        navController.navigate("calendar")
                                    }
                                }
                            },

                        shape = RoundedCornerShape(20.dp),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        ),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),

                            horizontalAlignment = Alignment.CenterHorizontally,

                            verticalArrangement = Arrangement.Center
                        ) {

                            when (item.title) {

                                "Book Ground" -> {

                                    Icon(
                                        imageVector = Icons.Default.SportsCricket,
                                        contentDescription = null,
                                        tint = Color(0xFF2E7D32),
                                        modifier = Modifier.size(52.dp)
                                    )
                                }

                                "Challenge Board" -> {

                                    Icon(
                                        imageVector = Icons.Default.Groups,
                                        contentDescription = null,
                                        tint = Color(0xFFD84315),
                                        modifier = Modifier.size(52.dp)
                                    )
                                }

                                "Score Wall" -> {

                                    Icon(
                                        imageVector = Icons.Default.Scoreboard,
                                        contentDescription = null,
                                        tint = Color(0xFF6A1B9A),
                                        modifier = Modifier.size(52.dp)
                                    )
                                }

                                "Ground Calendar" -> {

                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = Color(0xFF1565C0),
                                        modifier = Modifier.size(52.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = item.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}