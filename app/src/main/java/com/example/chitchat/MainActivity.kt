package com.example.chitchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chitchat.screens.ChatListScreen
import com.example.chitchat.screens.LoginScreen
import com.example.chitchat.screens.SignUp
import com.example.chitchat.ui.theme.ChitChatTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(var route: String) {
    object SignUp : DestinationScreen("signUp")
    object Login : DestinationScreen("login")
    object Profile : DestinationScreen("profile")
    object ChatList : DestinationScreen("chatList")
    object SingleChat : DestinationScreen("singleChat/{chatId}") {
        fun createRoute(id: String) = "singleChat/${id}"
    }

    object StatusList : DestinationScreen("statusList")
    object SingleStatus : DestinationScreen("singleStatus/{statusId}") {
        fun createRoute(userid: String) = "singleStatus/${userid}"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChitChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }
            }
        }
    }
}

@Composable
fun ChatAppNavigation() {
    val navController = rememberNavController()

    val vm = hiltViewModel<LCViewModel>()


    NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route) {
        composable(DestinationScreen.SignUp.route) {
            SignUp(navController, vm)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen()
        }
        composable(DestinationScreen.ChatList.route) {
            ChatListScreen()
        }
    }

}
