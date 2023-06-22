package com.example.datatheoremexercise

import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

class UrlDao {
    val urls = arrayListOf<String>()
    val filename = "./sdcard/urls.json"
    val reader = BufferedReader(FileReader(filename))
    val writer = BufferedWriter(FileWriter(filename))

    fun getAllUrls(): List<String> {
        val jsonString = reader.use {
            it.readText()
        }
        val urlsJson = JSONObject(jsonString).getJSONArray("urls")
        for (urlIndex in 0 until urlsJson.length()) {
            val url = urlsJson[urlIndex].toString()
            urls.add(url)
        }
        return urls
    }

    fun addUrl(url: String) {
        writer.use {
            it.write(url)
            it.newLine()
        }
    }

    fun flushStream() {
        writer.flush()
    }

    fun closeStream() {
        reader.close()
        writer.close()
    }
}