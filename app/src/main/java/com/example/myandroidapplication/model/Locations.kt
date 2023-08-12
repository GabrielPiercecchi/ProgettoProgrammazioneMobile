package com.example.myandroidapplication.model

data class Location (
    val id: Int,
    val name: String,
    val isCountry: Boolean
)

class Locations (val locations: List<Location>)