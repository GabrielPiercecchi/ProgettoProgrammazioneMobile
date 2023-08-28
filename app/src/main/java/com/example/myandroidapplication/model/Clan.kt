package com.example.myandroidapplication.model

data class Clan(
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val name: String,
    val tag: String
)

class Clans(val items: List<Clan>)