package com.example.restaurentappmvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.restaurentappmvvm.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser


class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private var authAppRepository = AuthAppRepository(application)
    private var userLiveData = authAppRepository.getUserLiveData()
    private var progressbarObservable = authAppRepository.getProgressLiveData()
    private var btnObservable = authAppRepository.getBtLiveData()

    fun signUp(email: String, password: String,view: View) {
        authAppRepository.signUp(email, password,view)
    }
    fun ifCurrentUserExist(view: View){
        authAppRepository.ifCurrentUserExist(view)
    }

    fun getUserLiveDate(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getProgressLiveData(): MutableLiveData<Boolean> {
        return progressbarObservable
    }

    fun getBtLiveData(): MutableLiveData<Boolean> {
        return btnObservable
    }

}