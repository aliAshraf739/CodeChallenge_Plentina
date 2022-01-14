package com.example.codechallenge_plentina.network

import com.example.codechallenge_plentina.model.Animal
import com.example.codechallenge_plentina.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class RestApi {

    companion object {
        private var api: Api? = null

        fun getService(): Api? {
            if (api == null) {
                val logging = HttpLoggingInterceptor()
                val httpClient = OkHttpClient.Builder()
                httpClient.readTimeout(20, TimeUnit.SECONDS)
                httpClient.connectTimeout(20, TimeUnit.SECONDS)
                httpClient.addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                api = retrofit.create(Api::class.java)
            }
            return api
        }

    }

    interface Api {
        @GET(Constants.GET_ANIMALS_LIST)
        fun getAnimalsList(@Path(value = "number", encoded = false) number: Int): Call<List<Animal>?>?
    }

}