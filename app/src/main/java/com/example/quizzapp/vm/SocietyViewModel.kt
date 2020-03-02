package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.Responses.SocietiesEntity
import com.example.quizzapp.objects.Responses.SocietyResponse

class SocietyViewModel(application: Application) : AndroidViewModel(application)
{


    val societies : MutableLiveData<SocietyResponse> by lazy {
        MutableLiveData<SocietyResponse>()
    }

    val selectedSociety : MutableLiveData<SocietiesEntity> by lazy {
        MutableLiveData<SocietiesEntity>()
    }

    fun getSocieties(){

        NetModule.getAllSocietiesRelatedToUser(societies)

    }
    fun leaveSociety(){
        NetModule.leaveSociety(selectedSociety.value?.id,societies)
    }
}