package com.example.myandroidapplication.model

class User {
    var apiKey: String? = null
    var tag: String? = null
    var name: String? = null
    var email: String? = null
    var uid: String? = null

    constructor(){}

    constructor(apiKey: String?, tag: String?, name: String?, email: String?, uid: String?){
        this.apiKey = apiKey
        this.tag = tag
        this.name = name
        this.email = email
        this.uid = uid
    }
}