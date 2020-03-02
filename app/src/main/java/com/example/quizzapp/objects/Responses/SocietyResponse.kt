package com.example.quizzapp.objects.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SocietyResponse {

    @SerializedName("societiesEntities")
    @Expose
    var societiesEntities: List<SocietiesEntity>? = null

}