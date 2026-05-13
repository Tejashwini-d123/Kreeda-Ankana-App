package com.example.kreedaankana.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen() {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // 👤 MANUAL TEAM NAME
    var teamName by remember { mutableStateOf("") }

    // 📅 MANUAL DATE
    var date by remember { mutableStateOf("") }

    // 🏟️ GROUND
    var groundName by remember { mutableStateOf("") }

    // ⏰ TIME SLOT
    var timeSlot by remember { mutableStateOf("") }

    var groundExpanded by remember { mutableStateOf(false) }
    var timeExpanded by remember { mutableStateOf(false) }

    // 🏟️ GROUND LIST
    val grounds = listOf(
        "Ankane Village Cricket Ground",
        "Kreeda Sports Arena",
        "Rural Unity Stadium",
        "Community Sports Complex",
        "Greenfield Sports Ground",
        "Ankana Volleyball Court"
    )

    // ⏰ TIME SLOT LIST
    val timeSlots = listOf(
        "6 AM - 8 AM",
        "8 AM - 10 AM",
        "10 AM - 12 PM",
        "2 PM - 4 PM",
        "4 PM - 6 PM",
        "6 PM - 8 PM"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Book Ground Slot",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 👤 TEAM NAME
        OutlinedTextField(
            value = teamName,
            onValueChange = { teamName = it },
            label = { Text("Team Name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 📅 DATE
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (dd-MM-yyyy)") },
            placeholder = { Text("Enter date") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🏟️ GROUND DROPDOWN
        ExposedDropdownMenuBox(
            expanded = groundExpanded,
            onExpandedChange = {
                groundExpanded = !groundExpanded
            }
        ) {

            OutlinedTextField(
                value = groundName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Ground Name") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = groundExpanded
                    )
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = groundExpanded,
                onDismissRequest = {
                    groundExpanded = false
                }
            ) {

                grounds.forEach { ground ->

                    DropdownMenuItem(
                        text = {
                            Text(ground)
                        },
                        onClick = {

                            groundName = ground
                            groundExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ⏰ TIME SLOT DROPDOWN
        ExposedDropdownMenuBox(
            expanded = timeExpanded,
            onExpandedChange = {
                timeExpanded = !timeExpanded
            }
        ) {

            OutlinedTextField(
                value = timeSlot,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Time Slot") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = timeExpanded
                    )
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = timeExpanded,
                onDismissRequest = {
                    timeExpanded = false
                }
            ) {

                timeSlots.forEach { slot ->

                    DropdownMenuItem(
                        text = {
                            Text(slot)
                        },
                        onClick = {

                            timeSlot = slot
                            timeExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📌 BOOK BUTTON
        Button(
            onClick = {

                val cleanTeam = teamName.trim()
                val cleanDate = date.trim()
                val cleanGround = groundName.trim()
                val cleanSlot = timeSlot.trim()

                if (
                    cleanTeam.isNotEmpty() &&
                    cleanDate.isNotEmpty() &&
                    cleanGround.isNotEmpty() &&
                    cleanSlot.isNotEmpty()
                ) {

                    // 🚫 CHECK DUPLICATE BOOKING
                    db.collection("bookings")
                        .whereEqualTo("groundName", cleanGround)
                        .whereEqualTo("date", cleanDate)
                        .whereEqualTo("timeSlot", cleanSlot)
                        .get()

                        .addOnSuccessListener { result ->

                            if (result.isEmpty) {

                                // ✅ NEW BOOKING
                                val booking = hashMapOf(
                                    "teamName" to cleanTeam,
                                    "groundName" to cleanGround,
                                    "date" to cleanDate,
                                    "timeSlot" to cleanSlot
                                )

                                db.collection("bookings")
                                    .add(booking)

                                    .addOnSuccessListener {

                                        Toast.makeText(
                                            context,
                                            "Booking Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        // ✅ CLEAR ALL FIELDS
                                        teamName = ""
                                        date = ""
                                        groundName = ""
                                        timeSlot = ""
                                    }

                                    .addOnFailureListener {

                                        Toast.makeText(
                                            context,
                                            "Booking Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            } else {

                                Toast.makeText(
                                    context,
                                    "Slot Already Booked",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        .addOnFailureListener {

                            Toast.makeText(
                                context,
                                "Error Checking Booking",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                } else {

                    Toast.makeText(
                        context,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {

            Text("Book Slot")
        }
    }
}