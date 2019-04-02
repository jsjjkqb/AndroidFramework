package com.app.leiving.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.leiving.model.BaseModel
import com.app.leiving.util.AlertDialogUtil
import com.app.leiving.util.PermissionUtil
import com.app.leiving.util.SysUtil

abstract class BaseActivity<T : BaseModel> : AppCompatActivity(), BaseView {

    companion object {
        fun <T> startAct(mActivity: AppCompatActivity, mClass: Class<T>) {
            mActivity.startActivity(Intent(mActivity, mClass))
        }
        fun <T> startActWithBundle(mActivity: AppCompatActivity, mClass: Class<T>, mBundle: Bundle) {
            var mIntent = Intent(mActivity, mClass)
            mIntent.putExtras(mBundle)
            mActivity.startActivity(mIntent)
        }
    }

    protected lateinit var mRootView: View
    protected var mWindowWidth: Int = 0
    private var canScroll: Boolean = false
    private var objAnimator: ObjectAnimator? = null
    protected lateinit var mModel: T
    protected var mDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window设置
        initWindow()
        setContentView(getLayoutId())
        // 初始化界面
        initView()
        // 初始化Model
        mModel = initModel()
        // 初始化监听事件
        initListener()
        // 初始化界面数据
        initData()
    }

    /**
     * 设置全屏
     */
    open fun setFullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 模拟沉浸式，不能和全屏一起设置
     * @param mToolbar
     */
    open fun setToolbarImmersive(mToolbar: Toolbar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val statusBarHeight = SysUtil.getStatusBarHeight(applicationContext)
            var layoutParams =  mToolbar.layoutParams
            layoutParams.height = SysUtil.getActionBarHeight(applicationContext) + statusBarHeight
            mToolbar.setPadding(0, statusBarHeight, 0, 0)
        }
    }

    open fun initWindow() {}

    abstract fun getLayoutId(): Int

    open fun initView() {
        mRootView = window.decorView
        mWindowWidth = resources.displayMetrics.widthPixels
    }

    abstract fun initModel() : T

    open fun initListener() {}

    open fun initData() {}

    /**
     * 用于判断该界面是否需要右滑关闭界面
     */
    abstract fun canMoveToClose(): Boolean

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN && ev.x <= mWindowWidth / 10) {
            canScroll = true
        }
        if (canMoveToClose() && canScroll) {
            return onTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 右滑关闭界面
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (objAnimator != null && objAnimator!!.isRunning) {
                    objAnimator!!.cancel()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                mRootView.translationX = event.x
            }
            MotionEvent.ACTION_UP -> {
                canScroll = false
                if (event.x >= mWindowWidth / 3) {
                    objAnimator = ObjectAnimator.ofFloat(mRootView, "translationX", event.x, mWindowWidth * 1f)
                    objAnimator!!.addListener(object: AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            finish()
                        }
                    })
                } else {
                    objAnimator = ObjectAnimator.ofFloat(mRootView, "translationX", event.x, 0f)
                }
                objAnimator!!.setDuration(300).start()
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 申请权限测试
     * @param permissions 权限列表
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
                             negativeMsg: String?,  negativeCb: DialogInterface.OnClickListener?,
                             neutralMsg: String?, neutralCb: DialogInterface.OnClickListener?) {
        mDialog = AlertDialogUtil.showAlertDialog(this, msg, positiveMsg, positiveCb, title, negativeMsg, negativeCb, neutralMsg, neutralCb)
        var mAlertController = AlertDialogUtil.getAlertController(mDialog)
//        AlertDialogUtil.setTitleV1iew(mAlertController, View.TEXT_ALIGNMENT_CENTER)
        AlertDialogUtil.setMessageView(mAlertController, Gravity.CENTER)
    }

    /**
     * Toast提示
     * @param msg
     * @param duration
     */
    override fun toast(msg: String, duration: Int) {
        Toast.makeText(this, msg, duration).show()
    }
}
