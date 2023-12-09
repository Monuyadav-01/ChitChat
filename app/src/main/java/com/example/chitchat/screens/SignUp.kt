package com.example.chitchat.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chitchat.CheckSignedIn
import com.example.chitchat.CommonProgressBar
import com.example.chitchat.DestinationScreen
import com.example.chitchat.LCViewModel
import com.example.chitchat.R
import com.example.chitchat.navigateTo


@Composable
fun SignUp(navController: NavController, vm: LCViewModel) {
    CheckSignedIn(vm = vm, navController =navController )
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var nameState = remember {
                mutableStateOf(TextFieldValue())
            }
            var numberState = remember {
                mutableStateOf(TextFieldValue())
            }
            var emailState = remember {
                mutableStateOf(TextFieldValue())
            }
            var passwordState = remember {
                mutableStateOf(TextFieldValue())
            }
            val focus = LocalFocusManager.current
            Image(
                painter = painterResource(
                    id = R.drawable.whatsapp,
                ),
                contentDescription = "logo image",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(8.dp)
            )
            Text(
                text = "SignUp Here",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(value = nameState.value, onValueChange = {
                nameState.value = it
            }, label = { Text(text = "Name") }, modifier = Modifier.padding(8.dp))

            OutlinedTextField(value = numberState.value, onValueChange = {
                numberState.value = it
            }, label = { Text(text = "Number") }, modifier = Modifier.padding(8.dp))

            OutlinedTextField(value = emailState.value, onValueChange = {
                emailState.value = it
            }, label = { Text(text = "Email") }, modifier = Modifier.padding(8.dp))

            OutlinedTextField(value = passwordState.value, onValueChange = {
                passwordState.value = it
            }, label = { Text(text = "PassWord") }, modifier = Modifier.padding(8.dp))

            Button(onClick = {
                if (nameState.value.text.isEmpty() || numberState.value.text.isEmpty() || emailState.value.text.isEmpty() || passwordState.value.text.isEmpty()) {
                    Toast.makeText(context, "Please enter all details", Toast.LENGTH_LONG).show()
                } else {
                    vm.signUp(
                        nameState.value.text,
                        numberState.value.text,
                        emailState.value.text,
                        passwordState.value.text
                    )
                }

            }, modifier = Modifier.padding(7.dp)) {
                Text(text = "Resister")
            }
            Text(
                text = "Already a user? Go to Login ->",
                modifier = Modifier.clickable {
                    navigateTo(navController, DestinationScreen.Login.route)
                },
                color = Color.Blue
            )

        }


    }
    if(vm.inProgress.value){
        CommonProgressBar()
    }

}