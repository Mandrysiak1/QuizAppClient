package com.example.quizzapp.Services

import com.example.UserResponse
import com.example.quizzapp.objects.LoginData
import com.example.quizzapp.objects.NewGameBody
import com.example.quizzapp.objects.PostAnswerBody
import com.example.quizzapp.objects.RegisterData
import com.example.quizzapp.objects.Responses.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface GameService{




    @POST("/games/{gameID}/post_answers")
    fun sendData(@Path(value = "gameID", encoded = true) game_id : String, @Body body: PostAnswerBody) : Call<ResponseBody>

}