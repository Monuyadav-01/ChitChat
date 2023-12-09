package com.example.chitchat

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.chitchat.data.Events
import com.example.chitchat.data.USER_NODE
import com.example.chitchat.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
) : ViewModel() {



    var inProgress = mutableStateOf(false)
    var eventMutableState = mutableStateOf<Events<String>?>(null)
    var signIn = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)

    init {
        val currentUser = auth.currentUser
        signIn.value= currentUser !=null

        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun signUp(name: String, phoneNumber: String, email: String, password: String) {
        inProgress.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                signIn.value = true
                createOrUpdateProfile()
            } else {
                handleException(it.exception, "SignUp Failed")
            }
        }
    }

    private fun createOrUpdateProfile(
        name: String? = null, number: String? = null, imageUrl: String? = null
    ) {
        var uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageUrl = imageUrl ?: userData.value?.imageUrl

        )

        uid?.let {
            inProgress.value = true
            db.collection(USER_NODE).document(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    // update user data
                } else {
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProgress.value = false
                    getUserData(uid)
                }
            }.addOnFailureListener {
                handleException(it, "Cannot Retrieve User")
            }
        }
    }

    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->

            if (error != null) {
                handleException(error, "cannot retrieve user")
            }
            if (value != null) {
                var user = value.toObject<UserData>()
                userData.value = user
                inProgress.value = false
            }
        }
    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.d("LiveChat", "Live Chat Exception : ", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isNullOrEmpty()) errorMsg else customMessage

        eventMutableState.value = Events(message)
        inProgress.value = true

    }
}
