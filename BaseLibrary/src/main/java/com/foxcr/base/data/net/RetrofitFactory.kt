package com.foxcr.base.data.net
import com.foxcr.base.common.BaseConstant
import com.foxcr.base.utils.SPUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor(){
    companion object{
        val instance by lazy {
            RetrofitFactory()
        }
    }
    private val retrofit:Retrofit
    private val interceptor:Interceptor
    init {
        interceptor = Interceptor {
            chain ->
            val loginUsername = SPUtil.getString(BaseConstant.LOGINUSERNAME,"")
            val loginUserPassword = SPUtil.getString(BaseConstant.LOGINUSERPASSWORD,"")
            val request: Request
            request = if (loginUserPassword.isNullOrEmpty() && loginUsername.isNullOrEmpty()){
                chain.request().newBuilder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("charset","utf-8")
                    .build()
            }else{
                chain.request().newBuilder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("charset","utf-8")
                    .addHeader(BaseConstant.COOKIE,"loginUserName=${loginUsername}")
                    .addHeader(BaseConstant.COOKIE,"loginUserPassword=${loginUserPassword}")
                    .build()
            }

            chain.proceed(request)
        }

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BaseConstant.SERVER_ADDRESS)
            .client(initClient())
            .build()


    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initLogInterceptor())
            .addInterceptor(interceptor)
            .readTimeout(BaseConstant.REQUEST_TIME,TimeUnit.SECONDS)
            .connectTimeout(BaseConstant.REQUEST_TIME,TimeUnit.SECONDS)
            .build()
    }

    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service:Class<T>):T{
        return retrofit.create(service)
    }
}