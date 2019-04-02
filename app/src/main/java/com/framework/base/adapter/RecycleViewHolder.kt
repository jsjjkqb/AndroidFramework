package com.framework.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class RecycleViewHolder(view: View): RecyclerView.ViewHolder(view) {

    companion object {
        fun Get(mContext: Context, parent: ViewGroup, layoutId: Int): RecycleViewHolder {
            var view: View = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
            return RecycleViewHolder(view)
        }
    }

    private var mBinding: ViewDataBinding

    init {
        mBinding = DataBindingUtil.bind(itemView)!!
    }

    open fun <T: ViewDataBinding> getBinding(): T {
        return mBinding as T
    }
}