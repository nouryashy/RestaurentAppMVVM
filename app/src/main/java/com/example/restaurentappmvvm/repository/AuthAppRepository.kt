package com.example.restaurentappmvvm.repository

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.restaurentappmvvm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthAppRepository(application: Application) {
    private var application = Application()
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var userLiveData = MutableLiveData<FirebaseUser>()
    private var loggedOutLiveData = MutableLiveData<Boolean>()
    private var progressbarObservable = MutableLiveData<Boolean>()
    private var btnObservable = MutableLiveData<Boolean>()

    init {
        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }

    fun signIn(email: String, password: String, view: View) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressbarObservable.value = true
                    btnObservable.value = false
                    userLiveData.postValue(firebaseAuth.currentUser)
                    view.findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                } else {
                    progressbarObservable.value = false
                    btnObservable.value = true
                }
            }
    }

    fun signUp(email: String, password: String, view: View) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressbarObservable.value = true
                    btnObservable.value = false
                    userLiveData.postValue(firebaseAuth.currentUser)
                    view.findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                } else {
                    progressbarObservable.value = false
                    btnObservable.value = true
                }
            }
    }

    fun ifCurrentUserExist(view: View) {
        if (firebaseAuth.currentUser != null) {
            view.findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }


    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressbarObservable
    }

    fun getBtLiveData(): MutableLiveData<Boolean> {
        return btnObservable
    }

}