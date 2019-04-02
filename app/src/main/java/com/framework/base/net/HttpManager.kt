package com.app.leiving.net

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

abstract class HttpManager<T> {

    /**
     * 获取HttpService接口类
     */
    abstract fun getServiceClass(): Class<T>

    /**
     * 获取请求路径
     */
    abstract fun getBaseUrl(): String

    /**
     * 获取接口实例
     */
    fun getService() : T {
        var retrofit = Retrofit.Builder()
            .client(getClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(getBaseUrl())
            .build()
        return retrofit.create(getServiceClass())
    }

    open fun getClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .addNetworkInterceptor(getNetworkInterceptor())
            .build()
    }

    /**
     * 添加拦截器
     */
    open fun getInterceptor(): Interceptor{
        return Interceptor {
            var request = it.request()
            // 统一请求头
            var builder = request.newBuilder()
            // builder.addHeader("headName", "value")
            // 统一请求参数
            var urlBuilder: HttpUrl.Builder = request.url().newBuilder()
            // urlBuilder.addEncodedQueryParameter("queryName", "value")

            // 返回response
            var response = it.proceed(builder.url(urlBuilder.build()).build())
            // log
            return@Interceptor response
        }
    }

    /**
     * 添加网络拦截器
     * @param builder
     */
    open fun getNetworkInterceptor(): Interceptor{
        return Interceptor {
            var request = it.request()
            request.newBuilder().build()
            return@Interceptor it.proceed(request)
        }
    }
}