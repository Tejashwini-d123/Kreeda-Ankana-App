package com.example.kreedaankana.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignupScreen(navController: NavController) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Full Name
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Full Name")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Confirm Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            label = {
                Text("Confirm Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (
                    name.isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty() ||
                    confirmPassword.isEmpty()
                ) {

                    Toast.makeText(
                        context,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                if (password != confirmPassword) {

                    Toast.makeText(
                        context,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            // SAVE NAME IN FIREBASE AUTH
                            val profileUpdates = userProfileChangeRequest {
                                displayName = name
                            }

                            FirebaseAuth.getInstance().currentUser
                                ?.updateProfile(profileUpdates)

                            // SAVE USER DATA IN FIRESTORE
                            val userId = auth.currentUser?.uid

                            val userData = hashMapOf(
                                "name" to name,
                                "email" to email
                            )

                            if (userId != null) {

                                db.collection("users")
                                    .document(userId)
                                    .set(userData)
                            }

                            Toast.makeText(
                                context,
                                "Signup Successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate("login") {
                                popUpTo("signup") {
                                    inclusive = true
                                }
                            }

                        } else {

                            Toast.makeText(
                                context,
                                it.exception?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        ) {

            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = {
                navController.navigate("login")
            }
        ) {

            Text("Already have an account? Login")
        }
    }
}