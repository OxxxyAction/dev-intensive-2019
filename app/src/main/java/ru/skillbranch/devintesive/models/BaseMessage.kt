package ru.skillbranch.devintesive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory{
        var lastId = -1

        fun makeMessage(fromUser: User?, chat: Chat, date: Date = Date(),
                        type: MessageType, payload: Any?, isIncoming: Boolean = false): BaseMessage{
            lastId++
            return when(type){
                MessageType.IMAGE -> ImageMessage("$lastId", fromUser, chat,
                    date = date, image = payload as String, isIncoming = isIncoming)
                MessageType.TEXT -> TextMessage("$lastId", fromUser, chat,
                    date = date, text= payload as String, isIncoming = isIncoming)
            }
        }
    }
    enum class MessageType{
        TEXT,
        IMAGE
    }
}