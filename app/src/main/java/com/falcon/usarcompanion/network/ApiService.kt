package com.falcon.usarcompanion.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

private const val BASE_URL = "https://github.com/labmember003/usar_data/raw/master/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface YearDataApiService {
    @GET("YEAR_1/data.json")
    fun getFirstYearData():
            Deferred<FirstYearApiResponse>
    /*
    @GET("YEAR_2")
    fun getSecondYearData():
            Deferred<List<SecondYearApiResponse>>

    @GET("YEAR_3")
    fun getThirdYearData():
            Deferred<List<ThirdYearApiResponse>>

    @GET("YEAR_4")
    fun getFourthYearData():
            Deferred<List<FourthYearApiResponse>>
     */
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