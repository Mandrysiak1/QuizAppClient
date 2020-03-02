package com.example.quizzapp.Services

import com.example.quizzapp.objects.GetSocietiesRelatedToUserRequest
import com.example.quizzapp.objects.QuestionBody
import com.example.quizzapp.objects.Responses.AllQuestionResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface QuestionService{


    @POST("questions/addQuestion")
    fun addQuestion(@Body body: QuestionBody) : Call<ResponseBody>

    @POST("questions/getAllQuestionsToSociety")
    fun getAllQuestions(@Body body: QuestionBody) : Call<AllQuestionResponse>
}