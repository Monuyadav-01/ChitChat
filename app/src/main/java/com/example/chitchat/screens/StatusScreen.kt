package com.example.chitchat.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.chitchat.LCViewModel

@Composable
fun StatusScreen(navController: NavController,vm : LCViewModel) {
    BottomNavigationMenu(selectedItem = BottomNavigationMenuItem.STATUSLIST, navController = navController)
}