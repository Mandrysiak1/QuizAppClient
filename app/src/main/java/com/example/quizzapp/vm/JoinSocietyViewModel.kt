package com.example.quizzapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.Responses.SocietiesEntity
import com.example.quizzapp.objects.Responses.SocietyResponse

class JoinSocietyViewModel(application: Application) : AndroidViewModel(application)
{

    val societies : MutableLiveData<SocietyResponse> by lazy {
        MutableLiveData<SocietyResponse>()
    }

    val selectedSociety : MutableLiveData<SocietiesEntity> by lazy {
        MutableLiveData<SocietiesEntity>()
    }

    fun JoinScoiety()
    {
        NetModule.joinSociety(selectedSociety.value?.id,societies)
    }

    fun getAllSocieties() {

        NetModule.getAllSocieties(societies)
    }


}