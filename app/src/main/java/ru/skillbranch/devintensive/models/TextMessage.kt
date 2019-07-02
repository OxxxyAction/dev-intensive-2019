package ru.skillbranch.devintensive.models

import java.util.*

class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date = Date(),
    var text: String?
) : BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String {
        val incomingStringValue = if (isIncoming) "Получил" else "Отрпавил"
        return "$id ${from?.firstName} $incomingStringValue сообщение"
    }
}