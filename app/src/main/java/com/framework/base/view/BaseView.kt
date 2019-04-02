package com.app.leiving.view

import android.content.DialogInterface
import android.widget.Toast

interface BaseView {
    fun toast(msg: String, duration: Int = Toast.LENGTH_SHORT)

    fun showAlertDialog(msg: String, positiveMsg: String, positiveCb: DialogInterface.OnClickListener? = null,
                        title: String? = null,
                        negativeMsg: String? = null, negativeCb: DialogInterface.OnClickListener? = null,
                        neutralMsg: String? = null, neutralCb: DialogInterface.OnClickListener? = null)
}