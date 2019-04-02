package com.framework.test.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey var id: Long,
    var nickName: String
)