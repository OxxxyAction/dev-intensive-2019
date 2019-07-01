package ru.skillbranch.devintesive.utils

import ru.skillbranch.devintesive.extensions.humanizeDiff
import ru.skillbranch.devintesive.models.User
import ru.skillbranch.devintesive.models.UserView

fun User.toUserView(): UserView {

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = ""
    val status = if( lastVisit == null ) "Еще ни разу не был"
    else if(isOnline) "online"
    else "Последний раз был ${lastVisit?.humanizeDiff()}"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        initials = initials,
        avatar = avatar,
        status = status
    )
}


