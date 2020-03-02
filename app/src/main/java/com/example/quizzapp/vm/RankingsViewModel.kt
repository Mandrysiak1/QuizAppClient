package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.LobbyListItem
import com.example.quizzapp.objects.Responses.RankingResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.objects.SpinnerItem

class RankingsViewModel(application: Application) : AndroidViewModel(application){

    val data : MutableLiveData<RankingResponse> by lazy {
        MutableLiveData<RankingResponse>()

    }

    val currentSoc : MutableLiveData<SpinnerItem> by lazy {
        MutableLiveData<SpinnerItem>()
    }

    val societies : MutableLiveData<SocietyResponse> by lazy {
        MutableLiveData<SocietyResponse>()
    }


    fun getSocieties(){

        NetModule.getAllSocietiesRelatedToUser(societies)

    }

    fun getRankings(it:SpinnerItem ) {
        it?.id?.let { it1 -> NetModule.getRankings(it1, data) }

    }
}
