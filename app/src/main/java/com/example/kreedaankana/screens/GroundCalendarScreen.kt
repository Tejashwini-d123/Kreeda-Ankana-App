package com.example.kreedaankana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun GroundCalendarScreen() {

    val db = FirebaseFirestore.getInstance()

    var bookings by remember {
        mutableStateOf(listOf<Map<String, Any>>())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    // 🔥 REALTIME BOOKINGS
    LaunchedEffect(Unit) {

        isLoading = true

        db.collection("bookings")
            .addSnapshotListener { value, _ ->

                isLoading = false

                if (value != null) {

                    bookings = value.documents.map {
                        it.data ?: emptyMap()
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .padding(16.dp)
    ) {

        Text(
            text = "📅 Ground Calendar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()
            }

        } else {

            if (bookings.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No Ground Bookings Yet"
                    )
                }

            } else {

                LazyColumn {

                    items(bookings) { booking ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),

                            shape = RoundedCornerShape(18.dp),

                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),

                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE8F5E9)
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = "🏏 Team: ${booking["teamName"]}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "📍 Ground: ${booking["groundName"]}"
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "📅 Date: ${booking["date"]}"
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "⏰ Time Slot: ${booking["timeSlot"]}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}