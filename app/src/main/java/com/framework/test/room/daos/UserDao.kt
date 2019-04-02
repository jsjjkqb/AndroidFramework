package com.framework.test.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.framework.test.room.entities.User
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Completable

    @Query("SELECT * FROM user WHERE id = :userId")
    fun query(userId: Long): Flowable<User>



}