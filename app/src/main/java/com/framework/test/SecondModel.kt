package com.framework.test

import android.content.Context
import android.util.Log
import com.app.leiving.model.BaseModel
import com.app.leiving.util.RXUtil
import com.app.leiving.view.BaseView
import com.framework.test.room.database.MyRoomDatabase
import com.framework.test.room.entities.User
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.collections.HashMap

class SecondModel(view: BaseView) : BaseModel(view) {
    private val dispose = CompositeDisposable()
    private val disMap: HashMap<String, Disposable> = HashMap()

    fun clear() {
        disMap.clear()
        dispose.clear()
    }

    fun getUser(mContext: Context, userId: Long) {
        val d = MyRoomDatabase.getInstance(mContext).userDao().query(userId)
            .compose(RXUtil.io2Main())
            .subscribe({
                val d: Disposable? = disMap.remove("getUser")
                if (d != null) {
                    d.dispose()
                    dispose.delete(d)
                }
            }, {
                Log.i("SecondModel_Query", it.localizedMessage)
            })
        disMap.put("getUser", d)
        dispose.add(d)
    }

    fun setUser(mContext: Context, userId: Long, nickName: String) {
        val user = User(userId, nickName)
        val d = MyRoomDatabase.getInstance(mContext).userDao().insert(user)
            .compose(RXUtil.io2MainCom())
            .subscribe({
                val d: Disposable? = disMap.remove("setUser")
                if (d != null) {
                    d.dispose()
                    dispose.delete(d)
                }
            }, {
                Log.i("SecondModel_Insert", it.localizedMessage)
            })
        disMap.put("setUser", d)
        dispose.add(d)
    }
}