package com.example.chitchat.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.chitchat.CommonProgressBar
import com.example.chitchat.LCViewModel

@Composable
fun ProfileScreen(navController: NavController, vm: LCViewModel) {
    val inProgress = vm.inProgress.value
    if (inProgress) {
        CommonProgressBar()
    } else {

        Column {

            BottomNavigationMenu(
                selectedItem = BottomNavigationMenuItem.PROFILE,
                navController = navController
            )
        }

    }


}