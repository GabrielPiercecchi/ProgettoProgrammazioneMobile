package com.example.myandroidapplication.model

data class Location (
    val id: Int,
    val name: String,
    val isCountry: Boolean,
    val countryCode: String?
)

class Locations (val items: List<Location>, val paging: Paging)

data class Paging(val cursors: Cursors)

data class Cursors(val before: String?, val after: String?)