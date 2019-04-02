package com.framework.test.room.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.framework.test.room.daos.UserDao
import com.framework.test.room.entities.User
import com.framework.test.room.views.UserView

@Database(entities = arrayOf(User::class), views = arrayOf(UserView::class), version = 1, exportSchema = false)
abstract class MyRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile private var INSTANCE: MyRoomDatabase? = null

        fun getInstance(mContext: Context): MyRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(mContext).also { INSTANCE = it }
            }

        private fun buildDatabase(mContext: Context) =
            Room.databaseBuilder(mContext,
                MyRoomDatabase::class.java,
                "baseFramework.db")
                .build()
    }
}