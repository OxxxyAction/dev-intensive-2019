package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int) : String{
    var resultString = ""
    for ((index,char) in this.withIndex()) {
        if(index == length){
            break
        }else{
            resultString += char
        }
    }
    resultString += "..."
    return resultString
}

public fun String.stripHtml(): String{
    var result = this
    result = result.removeExtraWhiteSpaces()
    result = result.removeHtmlTags()
    result = result.removeEscapeSequences()
    return result
}

public fun String.removeExtraWhiteSpaces(): String{
    var result = ""
    var prevChar = ""
    for (currChar in this) {
        if(!(prevChar == " " && currChar ==' ')){
            result += currChar
        }
        prevChar = currChar.toString()
    }
    return result
}


private fun String.removeEscapeSequences(): String {
    var result = this
    for (s in escapeSeqSet) {
        if(result.contains(s)){
            result.replace(s, "")
        }
    }
    return result
}

public fun String.removeHtmlTags(strBeginIndex: Int = 0): String{
    var workString = this.substring(strBeginIndex)
    var resultString = this
    var indexOfStartBeginTag =  workString.indexOf("<")
    var indexOfStartEndTag =  workString.indexOf("</")
    var indexOfStartTag = if( indexOfStartBeginTag > 0 && indexOfStartEndTag > 0)
        kotlin.math.min(indexOfStartBeginTag, indexOfStartEndTag)
    else
        kotlin.math.max(indexOfStartBeginTag, indexOfStartEndTag)

    var indexOfEndTag = workString.indexOf(">")
    if(indexOfStartTag > 0 && indexOfEndTag > 0 && indexOfStartTag < indexOfEndTag){
        val localRes = resultString.removeHtmlTagInRange( indexOfStartTag + strBeginIndex, indexOfEndTag+ strBeginIndex)
        val indexDiff = resultString.length - localRes.length
        resultString = localRes
        val tempIndex = if(indexDiff > 0) indexOfStartTag else indexOfEndTag + 1
        return resultString.removeHtmlTags(strBeginIndex + tempIndex)
    }else if(indexOfStartTag > 0){
        return resultString.removeHtmlTags(indexOfStartTag)
    }else if(indexOfEndTag > 0){
        return resultString.removeHtmlTags(indexOfEndTag)
    }else{
        return this.trimIndent()
    }

}

fun String.removeHtmlTagInRange(startIndex : Int, endIndex: Int) : String {
    var tagStr = this.substring(startIndex + 1, endIndex)
    val slashIndex = tagStr.indexOf("/")
    if(slashIndex >= 0){
        tagStr = tagStr.substring(slashIndex+1)
    }
    val tagValue = tagStr.split(" ")[0]
    return if(htmlTagsSet.contains(tagValue)){
        this.removeRange(startIndex, endIndex + 1).removeExtraWhiteSpaces()
    }else{
        this
    }
}
private val escapeSeqSet = setOf<String>(
    "&amp;",
    "&gt;",
    "&quot;",
    "&apos;",
    "&lt;"
)

private val htmlTagsSet = setOf<String>(
    "!--",
    "!DOCTYPE",
    "a",
    "abbr",
    "acronym",
    "address",
    "applet",
    "area",
    "article",
    "aside",
    "audio",
    "b",
    "base",
    "basefont",
    "bdo",
    "big",
    "blockquote",
    "body",
    "br",
    "button",
    "canvas",
    "caption",
    "center",
    "cite",
    "code",
    "col",
    "colgroup",
    "datalist",
    "dd",
    "del",
    "dfn",
    "div",
    "dl",
    "dt",
    "em",
    "embed",
    "fieldset",
    "figcaption",
    "figure",
    "font",
    "footer",
    "form",
    "frame",
    "frameset",
    "head",
    "header",
    "h1",
    "h2",
    "h3",
    "h4",
    "h5",
    "h6",
    "hr",
    "html",
    "i",
    "iframe",
    "img",
    "input",
    "ins",
    "kbd",
    "label",
    "legend",
    "li",
    "link",
    "main",
    "map",
    "mark",
    "meta",
    "meter",
    "nav",
    "noscript",
    "object",
    "ol",
    "optgroup",
    "option",
    "p",
    "param",
    "pre",
    "progress",
    "q",
    "s",
    "samp",
    "script",
    "section",
    "select",
    "small",
    "source",
    "span",
    "strike",
    "strong",
    "style",
    "sub",
    "sup",
    "table",
    "tbody",
    "td",
    "textarea",
    "tfoot",
    "th",
    "thead",
    "time",
    "title",
    "tr",
    "u",
    "ul",
    "var",
    "video",
    "wbr"
)