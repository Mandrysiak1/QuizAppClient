package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.Questionb

class AddQuestionViewModel(application: Application) : AndroidViewModel(application)
{


    var socID : String = ""


    fun sendQuestion(question: Questionb)
    {

        NetModule.sendQuestion(question,socID)
    }
}