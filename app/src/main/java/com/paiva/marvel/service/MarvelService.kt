package com.paiva.marvel.service

import com.paiva.marvel.BuildConfig
import com.paiva.marvel.model.Heroes
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class MarvelService(private var api: MarvelApi) {

    suspend fun getHeroes(): Heroes = api.getHeroes(getTimeStamp(),
        BuildConfig.PUBLIC_API_KEY,
        getHashApiKey())

    private fun getHashApiKey(): String {
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        val input = timeStamp + BuildConfig.PRIVATE_API_KEY + BuildConfig.PUBLIC_API_KEY
        val messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM)

        return BigInteger(MESSAGE_DIGEST_SIGNUM, messageDigest.digest(input.toByteArray()))
            .toString(MESSAGE_DIGEST_RADIX)
            .padStart(MESSAGE_DIGEST_PAD_START, MESSAGE_DIGEST_PAD_CHAR)
    }

    private fun getTimeStamp(): String {
        return Timestamp(System.currentTimeMillis()).time.toString()
    }

    companion object {
        private const val MESSAGE_DIGEST_ALGORITHM = "MD5"
        private const val MESSAGE_DIGEST_SIGNUM = 1
        private const val MESSAGE_DIGEST_RADIX = 16
        private const val MESSAGE_DIGEST_PAD_START = 32
        private const val MESSAGE_DIGEST_PAD_CHAR = '0'
    }
}