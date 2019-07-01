package ru.skillbranch.devintesive.models

class UserView (
    val id: String,
    val fullName: String,
    val nickName: String,
    var avatar: String? = null,
    var status: String? = "offline",
    val initials: String?
    )
{
    override fun toString(): String {
        return "UserView(id='$id', fullName='$fullName', nickName='$nickName', avatar=$avatar, status=$status, initials=$initials)"
    }
}