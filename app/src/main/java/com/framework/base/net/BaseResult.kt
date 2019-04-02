package com.app.leiving.net

open class BaseResult<T> {
    var code: Int = 0
    var msg: String? = null
    var data: T? = null

    fun isOK(): Boolean {
        return code == 200
    }
}