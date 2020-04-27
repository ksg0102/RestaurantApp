package com.example.myapplication.apipackage

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val AUTH = "Basic "+ Base64.encodeToString("root:123456".toByteArray(), Base64.NO_WRAP)
    private var mInstance: RetrofitClient? = null
    private const val BASE_URL = "http://10.0.2.2:8080/MyApi/public/"
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization",
                    AUTH
                )
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        @Synchronized
        fun getInstance(): RetrofitClient? {
            if (RetrofitClient.mInstance == null) {
                RetrofitClient.mInstance = RetrofitClient
            }
            return RetrofitClient.mInstance
        }
        retrofit.create(Api::class.java)
    }
}