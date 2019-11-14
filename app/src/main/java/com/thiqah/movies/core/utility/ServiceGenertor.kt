package com.thiqah.movies.core.utility


import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.thiqah.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ServiceGenerator {

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(BASE_URL, serviceClass, null)
    }

    private fun <S> createService(
        baseURL: String,
        serviceClass: Class<S>, authToken: String?
    ): S {

        val httpClient = OkHttpClient.Builder()
        val builder = Retrofit.Builder()

        httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(logging)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        builder.baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())

        var interceptor: Interceptor


        if (!TextUtils.isEmpty(authToken)) {

            interceptor = Interceptor { chain ->
                val request = chain.request()?.newBuilder()
                    ?.addHeader("Authorization", authToken?.trim())
                    ?.addHeader("Content-Type", "application/json")
                    ?.build()
                chain.proceed(request)
            }
        } else {
            interceptor = Interceptor { chain ->
                val request = chain.request()?.newBuilder()
                    ?.addHeader("User-Agent", "Tooli")
                    ?.addHeader("Content-Type", "application/json")
                    ?.addHeader("Charset", "UTF-8")
                    ?.build()

                chain.proceed(request)


            }
        }

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor)

            builder.client(httpClient.build())
        }

        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }

    companion object{
        private const val BASE_URL = "http://www.mocky.io"
    }
}