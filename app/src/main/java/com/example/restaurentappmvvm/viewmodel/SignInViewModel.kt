package com.example.restaurentappmvvm.viewmodel

import android.app.Application
import android.view.View

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser


class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private var authAppRepository = AuthAppRepository(application)

    private var userLiveData=authAppRepository.getUserLiveData()
    private var logOutLiveData=authAppRepository.getLoggedOutLiveData()
    private var progressbarObservable=authAppRepository.getProgressLiveData()
    private var btnObservable=authAppRepository.getBtLiveData()

    fun signIn(email: String, password: String,view: View){
        authAppRepository.signIn(email,password,view)
    }

    fun logOut(){
        authAppRepository.signOut()
    }
    fun getUserLiveData(): MutableLiveData<FirebaseUser>{
        return userLiveData
    }
    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return logOutLiveData
    }
    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressbarObservable
    }
    fun getBtLiveData(): MutableLiveData<Boolean> {
        return btnObservable
    }

}
