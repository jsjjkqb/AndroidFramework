package com.app.leiving.model

import com.app.leiving.view.BaseView
import com.app.leiving.viewmodel.BaseViewModel

open class BaseModel {
    var mView: BaseView

    constructor(view: BaseView) {
        mView = view
    }
}