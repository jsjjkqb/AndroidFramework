package com.app.leiving.util

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RXUtil {
    companion object {
        fun <T> io2Main(): FlowableTransformer<T, T> {
            return  FlowableTransformer {
                it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }
        }

        fun io2MainCom(): CompletableTransformer {
            return CompletableTransformer {
                it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}