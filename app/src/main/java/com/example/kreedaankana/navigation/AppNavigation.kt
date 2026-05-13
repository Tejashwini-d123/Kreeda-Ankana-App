package com.example.kreedaankana.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kreedaankana.screens.BookingScreen
import com.example.kreedaankana.screens.ChallengeScreen
import com.example.kreedaankana.screens.DashboardScreen
import com.example.kreedaankana.screens.GroundCalendarScreen
import com.example.kreedaankana.screens.LoginScreen
import com.example.kreedaankana.screens.ScoreWallScreen
import com.example.kreedaankana.screens.SignupScreen
import com.example.kreedaankana.screens.SplashScreen
import com.example.kreedaankana.screens.ProfileScreen
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // 🚀 SPLASH SCREEN
        composable("splash") {
            SplashScreen(navController)
        }

        // 🔐 LOGIN SCREEN
        composable("login") {
            LoginScreen(navController)
        }

        // 📝 SIGNUP SCREEN
        composable("signup") {
            SignupScreen(navController)
        }

        // 🏠 DASHBOARD SCREEN
        composable("home") {
            DashboardScreen(navController)
        }

        // 🏟️ BOOKING SCREEN
        composable("booking") {
            BookingScreen()
        }

        // 📅 GROUND CALENDAR
        composable("calendar") {
            GroundCalendarScreen()
        }

        // ⚔️ CHALLENGE BOARD
        composable("challenge") {
            ChallengeScreen()
        }

        // 🏆 SCORE WALL
        composable("score") {
            ScoreWallScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}