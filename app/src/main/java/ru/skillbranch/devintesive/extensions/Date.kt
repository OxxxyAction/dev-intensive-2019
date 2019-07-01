package ru.skillbranch.devintesive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY  = 24 * HOUR

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"): String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: ExtTimeUnits): Date {
    var time = this.time

    time += when (units) {
        ExtTimeUnits.SECOND -> value * SECOND
        ExtTimeUnits.MINUTE -> value * MINUTE
        ExtTimeUnits.HOUR -> value * HOUR
        ExtTimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val secondsDiff = (date.time - this.time) / 1000L
    val tempMinute = 60
    val tempHour = tempMinute * 60
    val tempDay = tempHour * 24
    return when (secondsDiff){
        in 0 .. 1 -> "только что"
        in 1 .. 45 -> "несколько секунд назад"
        in 45 .. 75 -> "минуту назад"
        in 75 .. 45 * tempMinute -> {
            val minutes = secondsDiff.getClosestTimeUnitFromSeconds()
            "$minutes ${getStringForTUnitAndPlural(ExtTimeUnits.MINUTE, minutes.getPluralRule())} назад"
        }
        in 45 * tempMinute .. 75 * tempMinute -> "час назад"
        in 75 * tempMinute .. 22 * tempHour -> {
            val hours = secondsDiff.getClosestTimeUnitFromSeconds()
            "$hours ${getStringForTUnitAndPlural(ExtTimeUnits.HOUR, hours.getPluralRule())} назад"
        }
        in 22 * tempHour .. 26 * tempHour -> "день назад"
        in 26 * tempHour .. 360 * tempDay ->{
            val days = secondsDiff.getClosestTimeUnitFromSeconds()
            "$days ${getStringForTUnitAndPlural(ExtTimeUnits.DAY, days.getPluralRule())} назад"
        }
        in 360 * tempDay .. Long.MAX_VALUE -> "более года назад"

        in -45 .. -1 -> "через несколько секунд"
        in -75 .. -45 -> "через минуту"
        in -45 * tempMinute .. -75 -> {
            val minutes = secondsDiff.getClosestTimeUnitFromSeconds()
            "через $minutes ${getStringForTUnitAndPlural(ExtTimeUnits.MINUTE, minutes.getPluralRule())}"
        }
        in -75 * tempMinute .. -45 * tempMinute -> "через час"
        in -22 * tempHour .. -75 * tempMinute -> {
            val hours = secondsDiff.getClosestTimeUnitFromSeconds()
            "через $hours ${getStringForTUnitAndPlural(ExtTimeUnits.HOUR, hours.getPluralRule())}"
        }
        in -26 * tempHour .. -22 * tempHour -> "через день"
        in -360 * tempDay .. -26 * tempHour ->{
            val days = secondsDiff.getClosestTimeUnitFromSeconds()
            "через $days ${getStringForTUnitAndPlural(ExtTimeUnits.DAY, days.getPluralRule())}"
        }
        else -> "более чем через год"

    }
}
private fun Long.getClosestTimeUnitFromSeconds(): Long{
    val minutes = if( this < 0 ) (this/60 * -1) else (this/60)
    val hours = minutes/60
    val day = hours/24
    return when{
        day > 0 -> day
        hours > 0 -> hours
        minutes > 0 -> minutes
        else -> this
    }
}

private fun getStringForTUnitAndPlural(timeUnit : ExtTimeUnits, rule: PluralRules): String{
    return timeUnitWithPluralToString.first {
        it.first == timeUnit && it.second == rule }.third
}

fun Long.getPluralRule(): PluralRules{
    return when {
        this.isPluralRuleOne() -> PluralRules.ONE
        this.isPluralRuleFew() -> PluralRules.FEW
        else -> PluralRules.OTHER
    }
}

fun Long.isPluralRuleOne(): Boolean{
    val strLongValue = this.toString()
    return strLongValue.endsWith("1") && !strLongValue.endsWith("11")
}
fun Long.isPluralRuleFew(): Boolean{
    val strLongValue = this.toString()
    return strLongValue.endsWith("2") || strLongValue.endsWith("3")
            || strLongValue.endsWith("4")
}

private val timeUnitWithPluralToString = listOf(
    Triple( ExtTimeUnits.MINUTE, PluralRules.ONE,"минуту"),
    Triple( ExtTimeUnits.MINUTE, PluralRules.FEW,"минуты"),
    Triple( ExtTimeUnits.MINUTE, PluralRules.OTHER,"минут"),

    Triple( ExtTimeUnits.HOUR, PluralRules.ONE,"час"),
    Triple( ExtTimeUnits.HOUR, PluralRules.FEW,"часа"),
    Triple( ExtTimeUnits.HOUR, PluralRules.OTHER,"часов"),

    Triple( ExtTimeUnits.DAY, PluralRules.ONE,"день"),
    Triple( ExtTimeUnits.DAY, PluralRules.FEW,"дня"),
    Triple( ExtTimeUnits.DAY, PluralRules.OTHER,"дней")
)

enum class PluralRules{
    ONE,
    FEW,
    OTHER
}

enum class ExtTimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY,
}