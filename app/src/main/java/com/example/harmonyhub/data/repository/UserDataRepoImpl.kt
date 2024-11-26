package com.example.harmonyhub.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserDataRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): UserDataRepo {

    fun getUserDataRef(userId: String?): DocumentReference {
        val userRef = firestore.collection("users").document(userId.toString())
        return userRef
    }

    override fun getUserName() {
        // TODO("Not yet implemented")
    }

    override fun setUserName(userName: String, userId: String?) {
        val userMap = hashMapOf(
            "userName" to userName
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