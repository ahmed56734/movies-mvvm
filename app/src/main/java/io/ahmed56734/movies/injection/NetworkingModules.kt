package io.ahmed56734.movies.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ahmed56734.movies.BuildConfig
import io.ahmed56734.movies.data.remote.MoviesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

val networkingModule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }


    single {
        Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", "e8c2242b11aa2a73a8dc61e4d0fa57b5")//TODO hide api key
                .build()

            return@Interceptor chain.proceed(original.newBuilder().url(url).build())
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get())
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
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesApi::class.java)

    }


}