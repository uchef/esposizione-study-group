package com.example.data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class BibleVerse(
    val book_id: String?,
    val book_name: String?,
    val chapter: Int?,
    val verse: Int?,
    val text: String?
)

@JsonClass(generateAdapter = true)
data class BibleApiResponse(
    val reference: String?,
    val verses: List<BibleVerse>?,
    val text: String?,
    val translation_id: String?,
    val translation_name: String?
)

interface BibleApiService {
    @GET("{passage}")
    suspend fun getPassage(
        @Path("passage") passage: String,
        @Query("translation") translation: String? = null
    ): BibleApiResponse
}

object BibleApiClient {
    private const val BASE_URL = "https://bible-api.com/"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val service: BibleApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(BibleApiService::class.java)
    }
}
