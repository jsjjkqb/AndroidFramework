package com.app.leiving.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.app.leiving.model.BaseModel
import com.app.leiving.util.AlertDialogUtil
import com.app.leiving.util.PermissionUtil

abstract class BaseFragment<T : BaseModel> : Fragment(), BaseView {

    protected lateinit var mContext : Context
    protected var isFirstLoad : Boolean = true
    protected lateinit var mModel : T
    protected var mDialog: AlertDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container, savedInstanceState)
    }

    open abstract fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mModel = getModel()
    }

    abstract fun getModel() : T

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isFirstLoad) {
            // 显示到台前，这时可以加载数据
            initData()
        }
    }

    open fun initData() {
        // 避免重复加载，如果需要每次显示台前都加载可以设置isFirstLoad = true
        isFirstLoad = false
    }

    /**
     * 申请权限
     */
    open fun setPermissions(vararg permissions: String) {
        PermissionUtil.RequestPermissions(this, permissions)
    }

    /**
     * 申请权限结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    /**
     * Toast提示
     * @param msg
     * @param duration
     */
    override fun toast(msg: String, duration: Int) {
        Toast.makeText(mContext, msg, duration).show()
    }

    /**
     * 显示提示选择弹窗
     * @param title 标题
     * @param msg 需要确认的内容
     * @param positiveMsg 确认按钮文本
     * @param positiveCb 确认回调
     * @param neutralMsg 中立按钮文本
     * @param neutralCb 中立回调
     * @param negativeMsg 取消按钮文本
     * @param negativeCb 取消回调
     */
    override fun showAlertDialog(msg: String, positiveMsg: String, positiveCb: DialogInterface.OnClickListener?,
                                 title: String?,
                                 negativeMsg: String?, negativeCb: DialogInterface.OnClickListener?,
                                 neutralMsg: String?, neutralCb: DialogInterface.OnClickListener?) {
        mDialog = AlertDialogUtil.showAlertDialog(mContext, msg, positiveMsg, positiveCb, title, negativeMsg, negativeCb, neutralMsg, neutralCb)
        var mAlertController = AlertDialogUtil.getAlertController(mDialog)
//        AlertDialogUtil.setTitleV1iew(mAlertController, View.TEXT_ALIGNMENT_CENTER)
        AlertDialogUtil.setMessageView(mAlertController, Gravity.CENTER)
    }
}