package com.androidcodechallenge.tmdbexplorer.injection

import com.androidcodechallenge.tmdbexplorer.AppConfig
import com.androidcodechallenge.tmdbexplorer.BuildConfig
import com.androidcodechallenge.tmdbexplorer.storage.TokenStorage
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {


    @Singleton
    @Provides
    @Named("publicClient")
    fun getPublicRetrofitClient(appConfig: AppConfig): Retrofit {

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()

                .addInterceptor { chain: Interceptor.Chain? ->

                    val request: Request? = chain?.request()
                            ?.newBuilder()
                        ?.build()
                    chain?.proceed(request)
                }   .addInterceptor(RequestInterceptor())


        if (BuildConfig.DEBUG) {
            client.addInterceptor(logging)
        }
         client.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
                .baseUrl(appConfig.appUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build()

    }


    @Singleton
    @Provides
    @Named("authClient")
    fun getAuthRetrofitClient(appConfig: AppConfig, tokenStorage: TokenStorage ): Retrofit {

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY


        val client = OkHttpClient.Builder()

         client.addInterceptor { chain: Interceptor.Chain? ->

                    val request: Request? = chain?.request()
                            ?.newBuilder()
                             ?.build()

                    chain?.proceed(request)
                }
             .addInterceptor(RequestInterceptor())


        if (BuildConfig.DEBUG) {
            client.addInterceptor(logging)
        }
        client.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)


        return Retrofit.Builder()
                .baseUrl(appConfig.appUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build()

    }
}