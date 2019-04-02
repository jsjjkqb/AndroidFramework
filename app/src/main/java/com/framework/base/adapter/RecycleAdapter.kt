package com.framework.base.adapter

import android.content.Context
import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecycleAdapter<T>(private var mContext: Context, private var mDataSet: ArrayList<T>): RecyclerView.Adapter<RecycleViewHolder>() {

    private var mHolder: RecycleViewHolder? = null
    private var mHolders: SparseArray<RecycleViewHolder> = SparseArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        mHolder = getHolder(viewType)
        if (mHolder == null) {
            mHolder = RecycleViewHolder.Get(mContext, parent, getLayoutId(viewType))
            putHolder(viewType, mHolder!!)
        }
        return mHolder as RecycleViewHolder
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        convert(holder, mDataSet.get(position), position)
    }

    fun getHolder(viewType: Int): RecycleViewHolder? {
        return mHolders.get(viewType)
    }

    fun putHolder(viewType: Int, holder: RecycleViewHolder) {
        mHolders.put(viewType, holder)
    }

    abstract fun getLayoutId(viewType: Int): Int

    abstract fun convert(holder: RecycleViewHolder, data: T, position: Int)
}