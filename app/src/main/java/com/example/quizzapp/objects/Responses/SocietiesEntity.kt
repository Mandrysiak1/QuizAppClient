package com.example.quizzapp.objects.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SocietiesEntity {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null

}
