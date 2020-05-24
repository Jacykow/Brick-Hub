package com.gulij.brickhub.models

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class Inventory : ArrayList<Item>() {
    companion object {
        const val TAG = "INVENTORY"

        fun fromXMLString(xml: String): Inventory {
            val pullParserFactory = XmlPullParserFactory.newInstance()
            val xmlParser: XmlPullParser = pullParserFactory.newPullParser()

            xmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            xmlParser.setInput(StringReader(xml))
            return fromXML(xmlParser)
        }

        fun fromXML(xmlParser: XmlPullParser): Inventory {
            val value = Inventory()

            var eventType = xmlParser.next()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (xmlParser.name == Item.TAG) {
                            value.add(Item.fromXML(xmlParser))
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (xmlParser.name == TAG) {
                            return value
                        }
                    }
                }
                eventType = xmlParser.next()
            }
            return value
        }
    }
}