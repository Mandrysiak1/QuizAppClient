package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.Responses.AllQuestionResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.objects.SpinnerItem

class QuestionViewModel(application: Application) : AndroidViewModel(application)
{

    val societies: MutableLiveData<SocietyResponse> by lazy {
        MutableLiveData<SocietyResponse>()
    }

    val currentSoc : MutableLiveData<SpinnerItem> by lazy {
        MutableLiveData<SpinnerItem>()
    }

    fun getAllQuestion()
    {
       NetModule.getAllQuestionsToSociety(currentSoc.value?.id,questions)
    }



    val questions : MutableLiveData<AllQuestionResponse> by lazy {
        MutableLiveData<AllQuestionResponse>()
    }

    fun getSocieties(){

        NetModule.getAllSocietiesRelatedToUser(societies)

    }
}
