package com.example.rcyclerviewactivities

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

data class Feeds(val title: String?, val image: String?, val summary: String?) {
    override fun toString(): String = title!!
}


class XMLParser {
    private val ns: String? = null

    fun parse(inputStream: InputStream): List<Feeds> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readRssFeed(parser)
        }
    }

    private fun readRssFeed(parser: XmlPullParser): List<Feeds> {

        val feeds = mutableListOf<Feeds>()

        parser.require(XmlPullParser.START_TAG, ns, "rss")

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            if (parser.name == "channel") {
                parser.require(XmlPullParser.START_TAG, ns, "channel")
                var title: String? = null
                var image: String? = null
                var summary: String? = null

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue
                    }

                    if (parser.name == "item") {
                     parser.require(XmlPullParser.START_TAG, ns, "item")
                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.eventType != XmlPullParser.START_TAG) {
                            continue
                        }
                            when (parser.name) {
                                "title" -> title = readTitle(parser)
                                "media:thumbnail" -> image = readImage(parser)
                                "description" -> summary = readSummary(parser)
                            else -> skip(parser) //skipping the next
                        }
                      }
                        feeds.add(Feeds(title ,image,summary))
                   }else skip(parser)
                }
            } else {
                skip(parser)
          }
        }
        return feeds
    }

    private fun readSummary(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "description")
        val desc = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "description")
        return desc
    }

    private fun readImage(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "media:thumbnail")
        val thumbnailUrl = parser.getAttributeValue(null, "url")
        parser.nextTag()
        return thumbnailUrl
    }


    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}