package com.example.kreedaankana.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Home : BottomNavItem(
        "home",
        "Home",
        Icons.Default.Home
    )

    object Calendar : BottomNavItem(
        "calendar",
        "Calendar",
        Icons.Default.DateRange
    )

    object Challenge : BottomNavItem(
        "challenge",
        "Challenge",
        Icons.Default.Sports
    )

    object Score : BottomNavItem(
        "score",
        "Scores",
        Icons.Default.Star
    )
}