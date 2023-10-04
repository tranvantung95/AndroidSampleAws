package com.amazonaws.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CONNECT_TIME_OUT = 60L
const val READ_TIME_OUT = 60L
const val WRITE_TIME_OUT = 60L

val apiModule = module {
    fun provideTokenApiModule(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }
    factory {
        provideTokenApiModule(get())
    }
}

val retrofitModule = module {
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .enableComplexMapKeySerialization()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .setVersion(1.0)
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }

    fun provideNullEmptyConvertFactory(): NullOnEmptyConverterFactory {
        return NullOnEmptyConverterFactory()
    }

    fun provideOkhttpClient(
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .hostnameVerifier { _, _ -> true }
        .build()

    fun provideTokenRetrofit(
        client: OkHttpClient,
        nullOnEmptyConverterFactory: NullOnEmptyConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    single {
        provideTokenRetrofit(get(), get())
    }
    single {
        provideGson()
    }
    single {
        provideNullEmptyConvertFactory()
    }
    single {
        provideOkhttpClient()
    }
}

