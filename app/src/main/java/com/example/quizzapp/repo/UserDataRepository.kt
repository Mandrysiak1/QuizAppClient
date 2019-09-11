package com.example.quizzapp.repo


import com.example.quizzapp.objects.UserData
import com.example.quizzapp.repo.user.UserDataDbData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UserDataRepository(var db: UserDataDbData){


    fun fetchLoggedUser(): Observable<UserData> {
        return db.getLoggedUser()
    }

    fun authenticate(userId: String, pin: String): Observable<Boolean> {

        return Observable.just(true); // TODO: Real auth
    }
}