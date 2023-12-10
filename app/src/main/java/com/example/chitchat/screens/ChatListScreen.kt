package com.example.chitchat.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.chitchat.LCViewModel
import io.grpc.okhttp.internal.Platform.TlsExtensionType

@Composable
fun ChatListScreen(navController: NavController,vm : LCViewModel) {
   
    BottomNavigationMenu(selectedItem = BottomNavigationMenuItem.CHATLIST, navController = navController)
}