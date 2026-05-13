package com.example.kreedaankana.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.Date

@Composable
fun ChallengeScreen() {

    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var teamName by remember { mutableStateOf("") }
    var sportType by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var ground by remember { mutableStateOf("") }

    var challenges by remember {
        mutableStateOf(listOf<Map<String, Any>>())
    }

    // Reply states
    var replyText by remember { mutableStateOf("") }
    var selectedChallengeId by remember { mutableStateOf("") }
    var showReplyBox by remember { mutableStateOf(false) }

    // 🔥 REALTIME CHALLENGES
    LaunchedEffect(Unit) {

        db.collection("challenges")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { value, _ ->

                if (value != null) {

                    challenges = value.documents.map {

                        val data = it.data ?: emptyMap()

                        data + ("id" to it.id)
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Challenge Board ⚔️",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Post New Challenge",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 👤 TEAM NAME
        OutlinedTextField(
            value = teamName,
            onValueChange = {
                teamName = it
            },
            label = {
                Text("Team Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 🏏 SPORT TYPE
        OutlinedTextField(
            value = sportType,
            onValueChange = {
                sportType = it
            },
            label = {
                Text("Sport Type")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 💬 MESSAGE
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            label = {
                Text("Challenge Message")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 🏟️ GROUND
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

        // 🚀 POST BUTTON
        Button(
            onClick = {

                if (
                    teamName.isNotEmpty() &&
                    sportType.isNotEmpty() &&
                    message.isNotEmpty() &&
                    ground.isNotEmpty()
                ) {

                    val data = hashMapOf(
                        "teamName" to teamName,
                        "sportType" to sportType,
                        "message" to message,
                        "ground" to ground,
                        "timestamp" to Date()
                    )

                    db.collection("challenges")
                        .add(data)

                    Toast.makeText(
                        context,
                        "Challenge Posted",
                        Toast.LENGTH_SHORT
                    ).show()

                    // CLEAR
                    teamName = ""
                    sportType = ""
                    message = ""
                    ground = ""

                } else {

                    Toast.makeText(
                        context,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(12.dp)
        ) {

            Text("Post Challenge")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "All Challenges",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 📋 CHALLENGE LIST
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(challenges) { item ->

                val challengeId = item["id"].toString()

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF1F8E9)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "🏏 Team: ${item["teamName"]}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "🎯 Sport: ${item["sportType"]}"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "💬 ${item["message"]}"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "🏟️ Ground: ${item["ground"]}"
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // REPLY BUTTON
                        Button(
                            onClick = {

                                selectedChallengeId = challengeId
                                showReplyBox = true
                                replyText = ""
                            },

                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Text("Reply")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // 🔥 REALTIME REPLIES
                        val replies = remember {
                            mutableStateListOf<Map<String, Any>>()
                        }

                        LaunchedEffect(challengeId) {

                            db.collection("challenges")
                                .document(challengeId)
                                .collection("replies")
                                .addSnapshotListener { value, _ ->

                                    if (value != null) {

                                        replies.clear()

                                        replies.addAll(
                                            value.documents.map {
                                                it.data ?: emptyMap()
                                            }
                                        )
                                    }
                                }
                        }

                        replies.forEach {

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "↳ ${it["team"]}: ${it["text"]}"
                            )
                        }
                    }
                }
            }
        }

        // ✉️ REPLY BOX
        if (showReplyBox) {

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = replyText,
                onValueChange = {
                    replyText = it
                },
                label = {
                    Text("Write Reply")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {

                    if (replyText.isNotEmpty()) {

                        val reply = hashMapOf(
                            "team" to "Opponent Team",
                            "text" to replyText,
                            "time" to Date()
                        )

                        db.collection("challenges")
                            .document(selectedChallengeId)
                            .collection("replies")
                            .add(reply)

                        Toast.makeText(
                            context,
                            "Reply Sent",
                            Toast.LENGTH_SHORT
                        ).show()

                        replyText = ""
                        showReplyBox = false

                    } else {

                        Toast.makeText(
                            context,
                            "Enter reply",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp)
            ) {

                Text("Send Reply")
            }
        }
    }
}