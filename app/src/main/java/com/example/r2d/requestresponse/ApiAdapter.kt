package com.example.r2d.requestresponse

import android.content.Context
import com.example.r2d.BuildConfig
import com.example.r2d.requestresponse.NetworkHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ApiAdapter {

    companion object {

        @Volatile
        private var instance: ApiService? = null

        class NoInternetException(override var message: String) : Exception(message)

        fun getInstance(context: Context): ApiService? {


            return if (NetworkHelper.isNetworkAvaialble(context)) {

                getApiService()

            } else {
                throw NoInternetException("No internet connection")
            }

        }

        fun getApiService(): ApiService? {

            try {
                if (instance == null) { //if there is no instance available... create new one
                    synchronized(ApiAdapter::class.java)
                    {
                        instance = Retrofit.Builder()
                            .baseUrl(Const.BASE_URL)
                            .client(getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create()).build()
                            .create(ApiService::class.java)
                    }
                }
            } catch (ex: java.lang.Exception) {

            }

            return instance
        }

        fun getOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .retryOnConnectionFailure(true);

            val interceptor = Interceptor { chain ->

                var completeTime = "";
                var time = Date()
                try {
                    time = Calendar.getInstance().time
                    val outputFmt = SimpleDateFormat("yyyy-MM-dd kk:mm:ss",Locale.getDefault())
                    completeTime = outputFmt.format(time)
                }catch (ex : Exception){
                    //val msg = "Actual time : $time \n Formatted time : $completeTime \n ${ex.toString()}"
                    //AlertDialogHelper.showMessage()
                    ex.printStackTrace()
                }

//                val contentLanguage = LoginManager.getUserSelectedLanguage();
//                val authorization = Const.KEY_BEARER +" "+LoginManager.getAccessToken();

                val original = chain.request()
                val requestBuilder =
                    original.newBuilder().header(Const.KEY_APPKEY, Const.KEY_APPKEY_VALUE)
                       // .addHeader(Const.KEY_CONTENT_LANGUAGE,contentLanguage!!)
                       // .addHeader(Const.KEY_AUTHORIZATION,authorization)
                val request = requestBuilder.build()
                chain.proceed(request)
            }

            builder.addInterceptor(interceptor)

            if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
            }
            return builder.readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000, TimeUnit.SECONDS)
                .build()
        }

    }




}