package com.app.leiving.net

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * rxjava2的Call（Subscriber）
 */
abstract class HttpResultSubscriber<T>: Subscriber<BaseResult<T>> {

    override fun onComplete() {}

    override fun onSubscribe(s: Subscription?) {}

    override fun onNext(t: BaseResult<T>?) {
        onCallback(t)
    }

    override fun onError(t: Throwable?) {
        var result: BaseResult<T> = BaseResult()
        result.code = -1
        result.msg = t!!.localizedMessage
        onCallback(result)
    }

    abstract fun onCallback(result: BaseResult<T>?)
}