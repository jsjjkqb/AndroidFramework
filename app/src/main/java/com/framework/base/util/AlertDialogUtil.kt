package com.app.leiving.util

import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.lang.Exception

class AlertDialogUtil {
    companion object {

        /**
         * 显示弹窗
         * @param title 标题
         * @param msg 需要确认的内容
         * @param positiveMsg 确认按钮文本
         * @param positiveCb 确认回调
         * @param neutralMsg 中立按钮文本
         * @param neutralCb 中立回调
         * @param negativeMsg 取消按钮文本
         * @param negativeCb 取消回调
         */
        fun showAlertDialog(mContext: Context, msg: String,
                            positiveMsg: String, positiveCb: DialogInterface.OnClickListener? = null,
                            title: String? = null,
                            negativeMsg: String? = null,  negativeCb: DialogInterface.OnClickListener? = null,
                            neutralMsg: String? = null, neutralCb: DialogInterface.OnClickListener? = null): AlertDialog? {
            if (TextUtils.isEmpty(msg)) {
                return null
            }
            var mBuilder = AlertDialog.Builder(mContext)
            if (!TextUtils.isEmpty(title)) {
                mBuilder.setTitle(title)
            }
            mBuilder.setMessage(msg)
            if (!TextUtils.isEmpty(positiveMsg)) {
                mBuilder.setPositiveButton(positiveMsg, positiveCb)
            }
            if (!TextUtils.isEmpty(neutralMsg)) {
                mBuilder.setNeutralButton(neutralMsg, neutralCb)
            }
            if (!TextUtils.isEmpty(negativeMsg)) {
                mBuilder.setNegativeButton(negativeMsg, negativeCb)
            }
            return mBuilder.show()
        }

        /**
         * 反射拿到AlertDialog的mAlert，这个是View的控制器
         * 需要AlertDialog.show之后才能反射，否则以下的设置无效
         */
        fun getAlertController(mDialog: AlertDialog?): Any? {
            if (mDialog == null) {
                return null
            }
            try {
                var mAlert = AlertDialog::class.java.getDeclaredField("mAlert")
                mAlert.isAccessible = true
                var mAlertController = mAlert.get(mDialog)
                return mAlertController
            } catch (e: Exception) {
                Log.i("getAlertController", e.message)
                return null
            }
        }

        /**
         * 利用前面拿到的mAlert反射拿到mTitleView
         * 这样就可以设置内容的textAlignment、textColor、textSize等
         * 设置gravity无效是因为textAlignment影响
         */
        fun setTitleView(mAlertController: Any?, textAlignment: Int = 0, color: Int = 0, textSize: Float = 0f) {
            if (mAlertController == null) {
                return
            }
            try {
                var mTitle = mAlertController.javaClass.getDeclaredField("mTitleView")
                mTitle.isAccessible = true
                var mTitleView: TextView = mTitle.get(mAlertController) as TextView
                if (textAlignment != 0) {
                    mTitleView.textAlignment = textAlignment
                }
                if (color != 0) {
                    mTitleView.setTextColor(color)
                }
                if (textSize > 0) {
                    mTitleView.textSize = textSize
                }
            } catch (e: Exception) {
                Log.i("setTitleView", e.message)
            }
        }

        /**
         * 利用前面拿到的mAlert反射拿到mMessageView
         * 这样就可以设置内容的gravity、textColor、textSize等
         */
        fun setMessageView(mAlertController: Any?, gravity: Int = 0, color: Int = 0, textSize: Float = 0f) {
            if (mAlertController == null) {
                return
            }
            try {
                var mMessage = mAlertController.javaClass.getDeclaredField("mMessageView")
                mMessage.isAccessible = true
                var mMessageView: TextView = mMessage.get(mAlertController) as TextView
                if (gravity != 0) {
                    mMessageView.gravity = gravity
                }
                if (color != 0) {
                    mMessageView.setTextColor(color)
                }
                if (textSize > 0) {
                    mMessageView.textSize = textSize
                }
            } catch (e: Exception) {
                Log.i("setMessageView", e.message)
            }
        }
    }
}