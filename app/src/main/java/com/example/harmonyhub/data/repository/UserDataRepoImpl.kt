package com.example.harmonyhub.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserDataRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): UserDataRepo {

    fun getUserDataRef(userId: String?): DocumentReference {
        val userRef = firestore.collection("users").document(userId.toString())
        return userRef
    }

    override fun getUserInfor(callback: (String?, String?) -> Unit) {
        val userId = auth.currentUser?.uid
        val userRef = getUserDataRef(userId)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {

                    val username = document.getString("userName")
                    val email = document.getString("email")

                    Log.d("OwO", "DocumentSnapshot data: ${username}")
                    callback(username, email)
                } else {
                    Log.d("OwO", "No such document")
                    callback(null, null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
                callback(null, null)
            }
    }

    override fun setUserInfor(userName: String, email: String, userId: String?) {
        val userMap = hashMapOf(
            "userName" to userName,
            "email" to email
        )

        val userRef = getUserDataRef(userId)

        userRef.set(userMap)
            .addOnSuccessListener() {
                 Log.d(TAG, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error writing document", e)
            }
    }

    override fun getAlbums() {
        // TODO("Not yet implemented")
    }

    override fun getSongs() {
        // TODO("Not yet implemented")
    }

    override fun getSong() {
        // TODO("Not yet implemented")
    }
}