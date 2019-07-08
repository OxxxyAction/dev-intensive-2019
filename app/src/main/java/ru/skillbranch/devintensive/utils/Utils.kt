package ru.skillbranch.devintensive.utils

object Utils {
    const val emptyStr = ""
    private val transliterationMap: HashMap<String, String> = hashMapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
    )

    fun parseFullName(fullName: String?) : Pair<String?,String?>{
        val partOfName: List<String>? = fullName?.split(" ")
        val firstName = partOfName?.getOrNull(0).getNullIfEmpty()
        val lastName = partOfName?.getOrNull(1).getNullIfEmpty()

        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val payloadParts = payload.split(" ")
        var result = ""
        for ((i, singleWord) in payloadParts.withIndex()) {
            if(i > 0) {
                result += divider
            }
            for (charInWord in singleWord) {
                result += if (charInWord.isUpperCase()){
                    transliterationMap[charInWord.toLowerCase().toString()]?.capitalize() ?: charInWord
                }else{
                    transliterationMap[charInWord.toString()] ?: charInWord
                }
            }

        }
        return result
    }

    fun toInitials(firstName: String?, lastName: String?): String?{
        val firstLetter= firstName?.firstOrNull()?.toUpperCase().getLetterOrNull() ?: emptyStr
        val secondLetter = lastName?.firstOrNull()?.toUpperCase().getLetterOrNull() ?: emptyStr
        val result = "$firstLetter$secondLetter"
        return if(result.isEmpty()) null else result
    }

    private fun Char?.getLetterOrNull(): String?{
        return if(this?.isLetter() == true){
            this.toString()
        }else{
            emptyStr
        }
    }

    private fun String?.getNullIfEmpty(): String?{
        return if(this.isNullOrBlank()){
            null
        }else{
            this
        }
    }


}

