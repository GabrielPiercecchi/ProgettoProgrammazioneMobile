package com.example.myandroidapplication.model

class Message {
    var message: String? = null //per consentire valori nulli
    var senderId: String? = null

    constructor(){}

    constructor(message: String?, senderId: String?){
        this.message = message
        this.senderId = senderId
    }
}