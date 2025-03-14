package com.csaim.valorant_clutchguide

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    suspend fun retrieveVideos(
        map:String,
        agent:String,
        side:String,
        site:String
    ): List<VideoData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://csaimgod.pythonanywhere.com/videos/$map/$agent/$side/$site")
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




    suspend fun retrieveCommunityVideos(): List<VideoData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://csaimgod.pythonanywhere.com/videos/community/")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            Log.d("Video Fetch", "Response Body: $responseBody")  // Log the response body

            val jsonObject = JSONObject(responseBody ?: "{}")
            val videoList = mutableListOf<VideoData>()
            val videosArray = jsonObject.optJSONArray("videos")

            if (videosArray != null && videosArray.length() > 0) {
                for (i in 0 until videosArray.length()) {
                    val videoObject = videosArray.getJSONObject(i)
                    val name = videoObject.optString("name", "Untitled Video")
                    val url = videoObject.optString("url", "")
                    videoList.add(VideoData(name, url))
                }
                Log.d("Video Fetch", "Fetched ${videoList.size} videos.")  // Log how many videos were fetched
            } else {
                Log.e("Video Fetch", "No videos found in the response.")
            }

            return@withContext videoList
        } else {
            Log.e("Video Fetch", "Failed to fetch videos. Response code: ${response.code}")
            return@withContext emptyList()
        }
    }

    suspend fun postCommunityVideo(videoData: VideoData): Boolean = withContext(Dispatchers.IO) {
        try {
            val jsonObject = JSONObject().apply {
                put("name", videoData.name)
                put("url", videoData.url)
            }

            val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

            val request = Request.Builder()
                .url("https://csaimgod.pythonanywhere.com/videos/upload")
                .post(requestBody)
                .build()

            val response: Response = okHttpClient.newCall(request).execute()

            if (response.isSuccessful) {
                Log.d("Video Post", "Successfully posted video: ${videoData.name}")
                return@withContext true
            } else {
                Log.e("Video Post", "Failed to post video. Response code: ${response.code}")
                return@withContext false
            }
        } catch (e: Exception) {
            Log.e("Video Post", "Error posting video: ${e.message}")
            return@withContext false
        }
    }

    suspend fun uploadCommunityVideo(videoUri: Uri, context: Context): Boolean = withContext(Dispatchers.IO) {
        try {
            val videoFile = uriToFile(videoUri, context) ?: return@withContext false

            // Ensure file details are correct
            Log.d("Video Upload", "Uploading file: ${videoFile.name}, Size: ${videoFile.length()} bytes")

            val requestBody = videoFile.asRequestBody("video/mp4".toMediaTypeOrNull())

            // Send "file" instead of "video"
            val multipartBody = MultipartBody.Part.createFormData("file", videoFile.name, requestBody)

            // Include the required "name" field
            val nameBody = MultipartBody.Part.createFormData("name", videoFile.name)

            val multipartRequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(nameBody)  // Include "name"
                .addPart(multipartBody)  // Include the mp4 file
                .build()

            val request = Request.Builder()
                .url("https://csaimgod.pythonanywhere.com/videos/upload")
                .post(multipartRequestBody)
                .build()

            Log.d("Video Upload", "Sending request: $request")

            val response: Response = okHttpClient.newCall(request).execute()

            // Log full response
            val responseBody = response.body?.string()
            Log.d("Video Upload", "Response Code: ${response.code}")
            Log.d("Video Upload", "Response Body: $responseBody")

            if (response.isSuccessful) {
                Log.d("Video Upload", "Successfully uploaded video: ${videoFile.name}")
                return@withContext true
            } else {
                Log.e("Video Upload", "Failed to upload video. Response code: ${response.code}")
                return@withContext false
            }
        } catch (e: Exception) {
            Log.e("Video Upload", "Error uploading video: ${e.message}")
            return@withContext false
        }
    }











}
