package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.Services.WebSocketModule
import com.example.quizzapp.objects.LobbyListItem
import com.example.quizzapp.objects.Responses.LobbyResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.objects.SpinnerItem

class LobbyViewModel(application: Application) : AndroidViewModel(application)
{

    val currentLobbyItem: MutableLiveData<LobbyListItem> by lazy {
     MutableLiveData<LobbyListItem>()
    }
    val societies : MutableLiveData<SocietyResponse> by lazy {
        MutableLiveData<SocietyResponse>()
    }

    val currentSoc : MutableLiveData<SpinnerItem> by lazy {
        MutableLiveData<SpinnerItem>()
    }

    val lobbies : MutableLiveData<LobbyResponse> by lazy {
        MutableLiveData<LobbyResponse>()
    }
    val started : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getSocieties(){

        NetModule.getAllSocietiesRelatedToUser(societies)

    }

    fun getLobbies()
    {
        NetModule.getAllLobbies(lobbies,currentSoc.value.toString());
    }

    fun addplayer(position: Int) {
        (lobbies.value as LobbyResponse).let { it.lobbies[position]  }.let { currentSoc.value?.id?.let { it1 ->
            NetModule.addPlayer(it.id,
                it1,lobbies)
        } }


    }


    fun connectToLobby(it: LobbyListItem) {
        WebSocketModule.subscribetToGame(it.id)
    }

    fun sendData(it:LobbyListItem)
    {

       // WebSocketModule.sendEchoViaStomp(it.id)
    }

    fun createLobby() {
        NetModule.createLobby(currentSoc.value?.id,lobbies)
    }


}