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

    suspend fun retrieveVideos(site:String): List<VideoData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://csaimgod.pythonanywhere.com/videos/kj/atkSide/$site")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val jsonObject = JSONObject(responseBody ?: "{}")

            val videoList = mutableListOf<VideoData>()
            val videosArray = jsonObject.optJSONArray("videos")

            if (videosArray != null) {
                for (i in 0 until videosArray.length()) {
                    val videoObject = videosArray.getJSONObject(i)
                    val name = videoObject.optString("name", "Untitled Video")
                    val url = videoObject.optString("url", "")
                    videoList.add(VideoData(name, url))
                }
            }

            return@withContext videoList
        } else {
            Log.e("Video Fetch", "Failed to fetch videos")
            return@withContext emptyList()
        }
    }
}
