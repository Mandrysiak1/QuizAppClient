package com.example.quizzapp.vm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.LoginData
import com.example.quizzapp.repo.UserDataRepository
import com.example.quizzapp.repo.user.UserDataDbData
import okhttp3.*
import java.io.IOException
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



 class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {


    private var _token :String = ""

    var token : String
        get() = _token
        set(value){
            _token = value
        }

    fun authenticate(userLogin:String,userPasswd:String){
        Log.d("LogData",userLogin + userPasswd)
        NetModule.login(LoginData(userPasswd,userLogin))
    }

    companion object{
    private val TAG = ">>>AuthenticationVM"
    }

}