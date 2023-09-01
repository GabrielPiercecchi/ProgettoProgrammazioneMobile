package com.example.myandroidapplication.model

import java.io.FileDescriptor

data class Clan(
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val name: String,
    val tag: String
)

class Clans(val items: List<Clan>)

data class ClanExtended(
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val name: String,
    val tag: String,
    val type: String,
    val clanPoints: Int,
    val clanCapitalPoints: Int,
    val members: Int,
    val description: String,
    val labels: List<Label>,
)