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

    suspend fun retrieveVideo(): String? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://csaimgod.pythonanywhere.com/videos/kj/kjBindBcontainer.mp4")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            // Assuming the video is directly returned and not wrapped in a JSON
            val videoUrl = response.request.url.toString()  // Get the direct URL of the video
            return@withContext videoUrl
        } else {
            Log.e("Video Fetch", "Failed to fetch video")
            return@withContext null
        }
    }


}