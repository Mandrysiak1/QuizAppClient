package com.example.quizzapp.Services

import android.util.Log
import com.example.UserResponse
import com.example.quizzapp.objects.LoginData
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetModule {

    val url : String = "https://aqueous-everglades-60502.herokuapp.com/api/"

    var retrofit: Retrofit
    var client : OkHttpClient
    var serviceInterceptor : ServiceInterceptor = ServiceInterceptor()
    init {

         client = OkHttpClient.Builder()
        .addInterceptor(serviceInterceptor)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45,TimeUnit.SECONDS)
        .build()

        val builder = Retrofit.Builder().client(client)
                                    .baseUrl(url).addConverterFactory(GsonConverterFactory.create())

        retrofit = builder.build()
    }

    private val authService: AuthService = retrofit.create(AuthService::class.java)

    fun login(data:LoginData){


       authService.createAccount(data).enqueue( object :Callback<UserResponse> {
           override fun onFailure(call: Call<UserResponse>, t: Throwable) {
               Log.d("LogResponse","nie udane logowanie")
           }

           override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
            if(response.isSuccessful){
                Log.d("LogResponse","udane logowanie: " + response.body()?.username + " " + response.body()?.token)
                serviceInterceptor.token = response.body()?.token.toString()
            }else
            {
                Log.d("LogResponse","nie udane logowanie S")
            }
           }


       })


    }

    fun newLobby(){
        Log.d("LobbyREQUEST","x" + authService.newLobby().request().headers().toString())

        authService.newLobby().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful)
                {

                    Log.d("LogResponse","ID LOBBY: " + response.body()?.string())
                }else
                {
                    Log.d("LogResponse",response.errorBody()?.string())
                }
            }

        })
    }



}
