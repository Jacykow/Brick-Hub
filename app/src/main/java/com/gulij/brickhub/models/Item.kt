package com.gulij.brickhub.models

import org.xmlpull.v1.XmlPullParser

class Item {
    var alternate: String? = null
    var extra: String? = null
    var color: String? = null
    var qty: String? = null
    var itemType: String? = null
    var itemId: String? = null

    companion object {
        const val TAG = "ITEM"

        fun fromXML(xmlParser: XmlPullParser): Item {

            val value = Item()

            var eventType = xmlParser.next()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val field = XMLField.fromXml(xmlParser)
                        when (field.first) {
                            "ALTERNATE" -> {
                                value.alternate = field.second
                            }
                            "EXTRA" -> {
                                value.extra = field.second
                            }
                            "COLOR" -> {
                                value.color = field.second
                            }
                            "QTY" -> {
                                value.qty = field.second
                            }
                            "ITEMTYPE" -> {
                                value.itemType = field.second
                            }
                            "ITEMID" -> {
                                value.itemId = field.second
                            }
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