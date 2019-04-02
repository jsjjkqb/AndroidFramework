package com.app.leiving.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.leiving.view.BaseView

class PermissionUtil {
    companion object {
        var PermissionCode: Int = 6
        fun RequestPermissions(mActivity: AppCompatActivity, vararg permissions: Array<out  String>) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }
            if (checkSelfPermissions(mActivity, permissions.single())) {
                mActivity.requestPermissions(permissions.single(), PermissionCode)
            }
        }

        fun RequestPermissions(mFragment: Fragment, vararg permissions: Array<out  String>) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }
            if (checkSelfPermissions(mFragment.requireContext(), permissions.single())) {
                mFragment.requestPermissions(permissions.single(), PermissionCode)
            }
        }

        fun checkSelfPermissions(mContext: Context, permissions: Array<out String>) : Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return false
            }
            var needRequest = false
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                    needRequest = true
                }
            }
            return needRequest
        }

        fun onRequestPermissionsResult(mView: BaseView, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    mView.toast("您已拒绝该界面相关权限使用，如需体验请打开设置界面授权")
                    return
                }
            }
        }
    }
}