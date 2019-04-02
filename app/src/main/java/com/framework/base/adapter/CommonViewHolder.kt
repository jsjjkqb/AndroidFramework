package com.framework.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class CommonViewHolder {

    companion object {
        fun Get(mContext: Context, convertView: View?, parent: ViewGroup?, layoutId: Int): CommonViewHolder {
            if (convertView == null) {
                return CommonViewHolder(mContext, parent, layoutId)
            } else {
                var holder: CommonViewHolder = convertView.getTag() as CommonViewHolder
                return holder
            }
        }
    }

    private var convertView: View
    private var mBinding: ViewDataBinding

    constructor (mContext: Context, parent: ViewGroup?, layoutId: Int) {
        convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false)
        convertView.setTag(this)
        mBinding = DataBindingUtil.bind(convertView)!!
    }

    open fun <T> getBinding(): T where T : ViewDataBinding {
        return mBinding as T
    }

    open fun getContentView(): View {
        return convertView
    }
}