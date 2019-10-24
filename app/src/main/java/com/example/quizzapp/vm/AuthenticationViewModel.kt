package com.example.quizzapp.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class AuthenticationViewModel(application: Application) : AndroidViewModel(application){




    fun authenticate(userLogin:String,userPasswd:String){
        Log.d(TAG, "Zalogowano użytkownika: $userLogin")
    }

    companion object{
    private val TAG = ">>>AuthenticationVM"
    }
}