package com.example.quizzapp.Services

import com.example.UserResponse
import com.example.quizzapp.objects.*
import com.example.quizzapp.objects.Responses.RankingResponse
import com.example.quizzapp.objects.Responses.RegisterResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import okhttp3.ResponseBody
import org.checkerframework.framework.qual.PostconditionAnnotation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SocietyService{


    @POST("society/leaveSociety")
    fun  leaveSociety(@Body body : LeaveSocietyBody): Call<ResponseBody>


    @POST("society/addUserToSociety")
    fun  joinSociety(@Body body : JoinSocietyBody) : Call<ResponseBody>


    @POST("society/getAllSocieties")
    fun getAllSocietiesNorReletedToUser(@Body body: GetSocietiesRelatedToUserRequest) : Call<SocietyResponse>

    @POST("society/getRanking")
    fun getRankings(@Body body : LeaveSocietyBody) : Call<RankingResponse>






}