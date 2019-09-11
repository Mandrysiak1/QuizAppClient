package com.example.quizzapp.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "UserData")

data class UserData(
    @PrimaryKey @Json(name = "userId") var userId: String,
    @Json(name = "userName") var userName: String? = "",
    @Json(name = "currentAssetId") var currentAssetId: String? = ""

) {
    var loggedUserId: String = ""
}