package com.framework.test

import com.app.leiving.view.BaseActivity
import com.app.leiving.view.BaseView
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : BaseActivity<SecondModel>(), BaseView {

    override fun initWindow() {
        super.initWindow()
        setFullScreen()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_second
    }

    override fun initModel(): SecondModel {
        return SecondModel(this)
    }

    override fun canMoveToClose(): Boolean {
        return true
    }

    override fun initListener() {
        super.initListener()
        btn_set.setOnClickListener {
            mModel.getUser(applicationContext, 1)
        }
        btn_get.setOnClickListener {
            mModel.setUser(applicationContext, 1, "leiving")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mModel.clear()
    }
}