package com.example.kreedaankana.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Kreeda-Ankana Login",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (email.isNotEmpty() && password.isNotEmpty()) {

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {

                                Toast.makeText(
                                    context,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.navigate("home") {
                                    popUpTo("login") {
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

                } else {

                    Toast.makeText(
                        context,
                        "Enter Email and Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {

            Text("Login")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = {
                navController.navigate("signup")
            }
        ) {

            Text("Don't have an account? Sign Up")
        }
    }
}