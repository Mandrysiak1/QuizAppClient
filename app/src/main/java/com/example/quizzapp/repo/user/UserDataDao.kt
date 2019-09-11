package com.example.quizzapp.repo.user

import androidx.annotation.WorkerThread
import io.reactivex.Maybe
import androidx.room.*
import com.example.quizzapp.objects.UserData

@Dao
interface UserDataDao {

    @Query("SELECT * from UserData")
    fun getLoggedUser(): Maybe<UserData>

    @Query("SELECT * from UserData WHERE userId = :userId ")
    fun getUserById(userId: Int): Maybe<UserData>
}