package com.example.kreedaankana.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@Composable
fun ScoreWallScreen() {

    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var teamA by remember { mutableStateOf("") }
    var teamB by remember { mutableStateOf("") }
    var scoreA by remember { mutableStateOf("") }
    var scoreB by remember { mutableStateOf("") }
    var ground by remember { mutableStateOf("") }

    var matches by remember {
        mutableStateOf(listOf<Map<String, Any>>())
    }

    // 🔥 REALTIME MATCHES
    LaunchedEffect(Unit) {

        db.collection("matches")
            .addSnapshotListener { value, _ ->

                if (value != null) {

                    matches = value.documents.map {
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
            text = "🏆 Score Wall",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TEAM A
        OutlinedTextField(
            value = teamA,
            onValueChange = {
                teamA = it
            },
            label = {
                Text("Team A")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // TEAM B
        OutlinedTextField(
            value = teamB,
            onValueChange = {
                teamB = it
            },
            label = {
                Text("Team B")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // SCORE A
        OutlinedTextField(
            value = scoreA,
            onValueChange = {
                scoreA = it
            },
            label = {
                Text("Score A")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // SCORE B
        OutlinedTextField(
            value = scoreB,
            onValueChange = {
                scoreB = it
            },
            label = {
                Text("Score B")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // GROUND
        OutlinedTextField(
            value = ground,
            onValueChange = {
                ground = it
            },
            label = {
                Text("Ground Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // POST BUTTON
        Button(
            onClick = {

                if (
                    teamA.isEmpty() ||
                    teamB.isEmpty() ||
                    scoreA.isEmpty() ||
                    scoreB.isEmpty() ||
                    ground.isEmpty()
                ) {

                    Toast.makeText(
                        context,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                val score1 = scoreA.toIntOrNull() ?: 0
                val score2 = scoreB.toIntOrNull() ?: 0

                val winner =
                    if (score1 > score2) teamA
                    else if (score2 > score1) teamB
                    else "Draw"

                val match = hashMapOf(
                    "teamA" to teamA,
                    "teamB" to teamB,
                    "scoreA" to scoreA,
                    "scoreB" to scoreB,
                    "winner" to winner,
                    "ground" to ground,
                    "date" to java.text.SimpleDateFormat(
                        "dd-MM-yyyy",
                        Locale.getDefault()
                    ).format(Date())
                )

                db.collection("matches")
                    .add(match)

                Toast.makeText(
                    context,
                    "Match Result Posted",
                    Toast.LENGTH_SHORT
                ).show()

                // CLEAR
                teamA = ""
                teamB = ""
                scoreA = ""
                scoreB = ""
                ground = ""
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(12.dp)
        ) {

            Text("Post Match Result")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "📋 Match History",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {

            items(matches) { match ->

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
                            text = "🏏 ${match["teamA"]} vs ${match["teamB"]}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "📊 Score: ${match["scoreA"]} - ${match["scoreB"]}"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "🏆 Winner: ${match["winner"]}"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "📍 Ground: ${match["ground"]}"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "📅 Date: ${match["date"]}"
                        )
                    }
                }
            }
        }
    }
}