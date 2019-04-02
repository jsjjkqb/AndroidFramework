package com.framework.base.net

import com.app.leiving.net.BaseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * retrofit本身的Call
 */
abstract class HttpResultCallback<T> : Callback<BaseResult<T>> {

    override fun onFailure(call: Call<BaseResult<T>>, t: Throwable) {
        var result: BaseResult<T> = BaseResult()
        result.code = -1
        result.msg = t.localizedMessage
        onCallback(result)
    }

    override fun onResponse(call: Call<BaseResult<T>>, response: Response<BaseResult<T>>) {
        if (response.isSuccessful) {
            return onCallback(response.body())
        }
        var result: BaseResult<T> = BaseResult()
        result.code = -1
        result.msg = response.message()
        onCallback(result)
    }

    abstract fun onCallback(result: BaseResult<T>?)
}