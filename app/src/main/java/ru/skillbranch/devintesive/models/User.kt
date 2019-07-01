package ru.skillbranch.devintesive.models

import ru.skillbranch.devintesive.utils.Utils
import java.util.*

class User (
    val id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var rating : Int = 0,
    var respect : Int = 0,
    var lastVisit : Date? = Date(),
    var isOnline : Boolean = false){

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this ( id = id, firstName = "John", lastName = "Doe")

    init {
        println("It`s Alive! ${if(lastName==="Doe") "His name is $firstName $lastName"
        else "And hist name is $firstName $lastName !!!"}")
    }

    fun printMe()=
        println("""
            id: $id
            firstName: $firstName
            lastName: $lastName
            avatar: $avatar
            rating: $rating
            respect: $respect
            lastVisit: $lastVisit
            isOnline : $isOnline
        """.trimIndent())

    companion object Factory{
        private var lastUserId = -1

        fun makeUser(fullName: String) : User{
            lastUserId++

            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastUserId", firstName = firstName, lastName = lastName)
        }
    }

    class Builder(private val id: String){
        private var firstName : String? = null
        private var lastName : String? = null
        private var avatar : String? = null
        private var rating : Int = 0
        private var respect : Int = 0
        private var lastVisit : Date? = Date()
        private var isOnline : Boolean = false

        fun firstName ( firstName : String) = apply { this.firstName = firstName }
        fun lastName ( lastName : String) = apply { this.lastName = lastName }
        fun avatar ( avatar : String) = apply { this.avatar = avatar }
        fun rating ( rating : Int) = apply { this.rating = rating }
        fun respect ( respect : Int) = apply { this.respect = respect }
        fun lastVisit ( lastVisit : Date) = apply { this.lastVisit = lastVisit }
        fun isOnline ( isOnline : Boolean) = apply { this.isOnline = isOnline }
        fun build() = User(id, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
    }
}