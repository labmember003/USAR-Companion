package com.falcon.usarcompanion.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://github.com/labmember003/usar_data/raw/master/"

private val moshi = Moshi.Builder().build()

var okHttpClient = OkHttpClient().newBuilder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface YearDataApiService {
    @GET("YEAR_1/data.json")
    fun getFirstYearData():
            Deferred<FirstYearApiResponse>

    @GET("YEAR_2/data.json")
    fun getSecondYearData():
            Deferred<SecondYearApiResponse>

    @GET("YEAR_3/data.json")
    fun getThirdYearData():
            Deferred<ThirdYearApiResponse>

    @GET("YEAR_4/data.json")
    fun getFourthYearData():
            Deferred<FourthYearApiResponse>

}

interface FileApiService{
    @GET
    fun getFileData(@Url url: String): Deferred<ByteArray>
}

object Api {
    val fileApiService : FileApiService by lazy {
        retrofit.create(FileApiService::class.java)
    }
    val yearDataApiService: YearDataApiService by lazy {
        retrofit.create(YearDataApiService::class.java)
    }
}