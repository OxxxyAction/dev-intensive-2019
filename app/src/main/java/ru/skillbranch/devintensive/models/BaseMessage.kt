package ru.skillbranch.devintensive.models

import java.lang.IllegalStateException
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
                        type1: String, payload: Any?, isIncoming: Boolean = false): BaseMessage {
            lastId++
            return when(type1){
                "image" -> ImageMessage(
                    "$lastId", fromUser, chat,
                    date = date, image = payload as String, isIncoming = isIncoming
                )
                "text" -> TextMessage(
                    "$lastId", fromUser, chat,
                    date = date, text = payload as String, isIncoming = isIncoming
                )
                else -> throw IllegalStateException("unsupported type: $type1" )
            }
        }
    }
    enum class MessageType{
        TEXT,
        IMAGE
    }
}