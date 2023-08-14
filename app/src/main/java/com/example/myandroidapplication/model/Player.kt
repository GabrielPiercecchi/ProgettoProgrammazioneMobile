package com.example.myandroidapplication.model

data class Player(
    //val achievements: List<Achievement>,
    //val attackWins: Int,
    val bestTrophies: Int,
    val bestVersusTrophies: Int,
    val builderHallLevel: Int,
    val clan: Clan,
    //val clanCapitalContributions: Int,
    //val defenseWins: Int,
    val donations: Int,
    val donationsReceived: Int,
    val expLevel: Int,
    //val heroes: List<Heroe>,
    val labels: List<Label>,
    val league: League,
    val name: String,
    //val playerHouse: PlayerHouse,
    val role: String,
    //val spells: List<Spell>,
    val tag: String,
    val townHallLevel: Int,
    //val townHallWeaponLevel: Int,
    //val troops: List<Troop>,
    val trophies: Int,
    //val versusBattleWinCount: Int,
    //val versusBattleWins: Int,
    val versusTrophies: Int,
    //val warPreference: String,
    val warStars: Int
)

class Players(val players: List<Player>)