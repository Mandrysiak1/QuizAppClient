package com.example.quizzapp.repo.user

import com.example.quizzapp.objects.UserData
import io.reactivex.Observable

class UserDataDbData(private val dao:UserDataDao){


    fun getUserById(userId: Int): Observable<UserData> {
        return dao.getUserById(userId).toObservable()
    }

    fun getLoggedUser(): Observable<UserData> {
        return dao.getLoggedUser().toObservable()
    }
}

