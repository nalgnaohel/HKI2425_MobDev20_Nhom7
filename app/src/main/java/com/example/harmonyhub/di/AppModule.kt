package com.example.harmonyhub.di

import com.example.harmonyhub.data.repository.UserDataRepoImpl
import com.example.harmonyhub.domain.repository.UserDataRepo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//DI: stands for dependency injection, which is responsible for providing the dependencies to other app's components.

//Mark this object as a dagger module where provides  for Dagger Hilt
@Module
//Determine lifecycle scope of this module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideUserDataRepo(
        FirebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): UserDataRepo {
        return UserDataRepoImpl(
            FirebaseAuth,
            firestore
        )
    }
}