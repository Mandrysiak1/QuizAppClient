package com.example.quizzapp.Services

import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class Service(){

    companion object{
        val serverAddress = "https://aqueous-everglades-60502.herokuapp.com/api"
    }


}