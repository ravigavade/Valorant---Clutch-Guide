package com.csaim.valorant_clutchguide

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class VideoManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        okHttpClient = builder.build()
    }

    suspend fun retrieveVideos(): List<String> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://csaimgod.pythonanywhere.com/videos/kj/atkSide/siteB")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val jsonObject = JSONObject(responseBody ?: "{}")

            // Extract video URLs from JSON array
            val videoList = mutableListOf<String>()
            val videosArray = jsonObject.optJSONArray("videos")

            if (videosArray != null) {
                for (i in 0 until videosArray.length()) {
                    videoList.add(videosArray.getString(i))
                }
            }

            return@withContext videoList
        } else {
            Log.e("Video Fetch", "Failed to fetch videos")
            return@withContext emptyList()
        }
    }
}
