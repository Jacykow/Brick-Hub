package com.gulij.brickhub.models

import org.xmlpull.v1.XmlPullParser

class XMLField {
    companion object {
        fun fromXml(xmlParser: XmlPullParser): Pair<String, String> {
            val key = xmlParser.name
            xmlParser.next()
            val value = xmlParser.text
            xmlParser.next()
            return Pair(key, value)
        }
    }
}