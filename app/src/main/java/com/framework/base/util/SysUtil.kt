package com.app.leiving.util

import android.content.Context
import com.framework.test.R

class SysUtil {
    companion object {

        fun getStatusBarHeight(mContext: Context) : Int {
            var result : Int = 0
            var resourceId = mContext.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = mContext.resources.getDimensionPixelSize(resourceId)
            } else {
                result = dpToPx(mContext, 20f)
            }
            return result
        }

        fun getActionBarHeight(mContext: Context) : Int {
            return mContext.resources.getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material)
        }

        fun dpToPx(mContext: Context, dp : Float) : Int {
            return (dp * mContext.resources.displayMetrics.density + 0.5f).toInt()
        }

        fun pxToDp(mContext: Context, px: Float) : Int {
            return (px / mContext.resources.displayMetrics.density + 0.5f).toInt()
        }
    }
}