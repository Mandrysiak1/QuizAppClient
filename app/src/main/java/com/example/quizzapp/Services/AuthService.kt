package com.example.quizzapp.Services

import com.example.UserResponse
import com.example.quizzapp.objects.LoginData
import com.example.quizzapp.objects.RegisterData
import com.example.quizzapp.objects.Responses.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthService{

    @POST("auth/login")
    @Headers("No-Authentication: true")
    fun  createAccount(@Body data: LoginData): Call<UserResponse>



    @POST("auth/register")
    @Headers("No-Authentication: true")
    fun registerAccount(@Body data: RegisterData) : Call<RegisterResponse>



    @GET("lobby/new")
    fun  newLobby(): Call<ResponseBody>

}