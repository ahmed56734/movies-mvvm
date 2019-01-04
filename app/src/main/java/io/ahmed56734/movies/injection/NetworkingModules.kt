package io.ahmed56734.movies.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ahmed56734.movies.BuildConfig
import io.ahmed56734.movies.data.remote.MoviesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkingModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }


    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Gson> {
        GsonBuilder()
            //.excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get())
    }

    single<Retrofit.Builder> {
        Retrofit.Builder()
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
    }


    single<MoviesApi> {
        get<Retrofit.Builder>()
            .build()
            .create(MoviesApi::class.java)

    }


}