package com.paiva.marvel.base

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.lang.reflect.Type

open class BaseUnitTest {
    fun <T> getMockJson(jsonFile: String, type: Type): T {
        val file = readFile(jsonFile)

        return Gson().fromJson(file, type)
    }

    private fun readFile(jsonFile: String): Reader {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(jsonFile)

        return BufferedReader(InputStreamReader(inputStream))
    }
}