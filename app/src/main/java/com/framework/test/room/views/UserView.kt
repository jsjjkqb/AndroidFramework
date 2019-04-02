package com.framework.test.room.views

import androidx.room.DatabaseView

@DatabaseView("select * from User")
data class UserView (
    var id: Long,
    var nickName: String
)