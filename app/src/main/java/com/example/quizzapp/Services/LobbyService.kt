package com.example.quizzapp.Services

import com.example.quizzapp.objects.*
import com.example.quizzapp.objects.Responses.LobbyResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LobbyService{

    @POST("society/getUserSocieties")
    fun getAllSocietesReletedToUser(@Body body:GetSocietiesRelatedToUserRequest) : Call<SocietyResponse>

    @POST("lobby/getAllLobbies")
    fun getAllLobbiesRelatedToSociety(@Body body: LobToSocRequest) : Call<LobbyResponse>

    @POST("lobby/new")
    fun createNewLobby(@Body body : NewLobbyRequest) : Call<ResponseBody>

    @POST("lobby/addPlayer")
    fun addPlayer(@Body body : addPlayerBody) : Call <ResponseBody>

    @POST("/games/{gameID}/new_game")
    fun startGame(@Path(value = "gameID", encoded = true) game_id : String,@Body body: NewGameBody) : Call<ResponseBody>
}