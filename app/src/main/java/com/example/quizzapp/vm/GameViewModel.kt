package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.LobbyListItem
import com.example.quizzapp.objects.PostAnswerBody
import com.example.quizzapp.objects.Responses.GameState

class GameViewModel(application: Application) : AndroidViewModel(application){

    val currentGameState: MutableLiveData<GameState> by lazy {
        MutableLiveData<GameState>()
    }

    val gameStarted : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    var gameID : String = ""

    var isAnswered : Boolean = false;
    var answer : Char = 'a'
    var playerToInteger : HashMap<String, Int?> = HashMap()


    fun sendData()
    {
        NetModule.postAnswers(gameID, getState())
    }

    private fun getState(): PostAnswerBody {
        return PostAnswerBody(NetModule.username,gameID,answer,isAnswered,playerToInteger)
    }
}