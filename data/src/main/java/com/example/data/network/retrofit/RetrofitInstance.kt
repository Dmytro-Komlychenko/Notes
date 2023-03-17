package com.example.data.network.retrofit

import com.example.data.network.api.NoteApi
import com.example.data.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NoteApi by lazy {
        retrofit.create(NoteApi::class.java)
    }
}