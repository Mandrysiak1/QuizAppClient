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
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.objects.RegisterData


class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {

    val login : MutableLiveData<String> by lazy {
         MutableLiveData<String>()
     }

    val passwd: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    val isLogged: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isRegistered: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun authenticate(){
        NetModule.login(LoginData(passwd.value.toString(),login.value.toString()),isLogged)
    }

    fun register(email:String,passwd:String)
    {
        NetModule.register(RegisterData(email,passwd),isRegistered)
    }

    companion object{
    private val TAG = ">>>AuthenticationVM"
    }

}