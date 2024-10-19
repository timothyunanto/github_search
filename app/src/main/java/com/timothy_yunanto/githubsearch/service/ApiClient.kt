package com.timothy_yunanto.githubsearch.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.timothy_yunanto.githubsearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private fun getOkHttpClient(): OkHttpClient {
            val logger = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                logger.level = HttpLoggingInterceptor.Level.BODY
            else
                logger.level = HttpLoggingInterceptor.Level.NONE

            return OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(
                    object : Interceptor {
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val request: Request = chain.request()
                                .newBuilder()
                                .header("Accept", "application/vnd.github+json")
                                .header("X-GitHub-Api-Version", "2022-11-28")
                                .header("Authorization", "")
                                .build()
                            return chain.proceed(request)
                        }
                    }
                )
                .addNetworkInterceptor(StethoInterceptor())
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .callTimeout(90, TimeUnit.SECONDS)
                .build()
        }

        private fun provideRetrofit(): Retrofit {
            val baseUrl = "https://api.github.com/"

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val httpClient = getOkHttpClient()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
        }

        fun provideApiService(): ApiService {
            return provideRetrofit().create(ApiService::class.java)
        }
    }
}