package com.example.quizzapp.objects.Responses

class RankingResponse(items: ArrayList<RankingItem>) {

    var items: ArrayList<RankingItem> = ArrayList()

    init {
        this.items = items
    }
}