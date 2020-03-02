package com.example.quizzapp.objects

class PostAnswerBody(
    var userID: String,
    var gameID: String,
    var answer: Char?,
    private var isAnswered: Boolean,// - nie zgadnie + zgadnie , 0 brak obstawienia
    var playerToInteger: HashMap<String, Int?>
) {

}