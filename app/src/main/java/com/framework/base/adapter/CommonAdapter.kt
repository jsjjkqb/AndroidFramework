package com.framework.base.adapter

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

abstract class CommonAdapter<T>(private var mContext: Context, private var mDataSet: ArrayList<T>): BaseAdapter() {

    private lateinit var mCreatedView: SparseArray<View>

    init {
        if (viewTypeCount > 1) {
            mCreatedView = SparseArray()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewType: Int = getItemViewType(position)
        var view: View? = getConvertView(viewType)
        var holder: CommonViewHolder = CommonViewHolder.Get(mContext, view, parent, getLayoutId(viewType))
        if (view == null) {
            view = holder.getContentView()
            putConvertView(viewType, view)
        }
        convert(holder, getItem(position), position)
        return view
    }

    override fun getItem(position: Int): T {
        return mDataSet.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mDataSet.size
    }

    /**
     * 返回ItemView的布局类型
     */
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    /**
     * 返回ItemView布局数
     */
    override fun getViewTypeCount(): Int {
        return super.getViewTypeCount()
    }

    /**
     * 根据ViewType获取View
     */
    fun getConvertView(viewType: Int): View? {
        if (viewTypeCount <= 1) {
            return null
        }
        return mCreatedView.get(viewType)
    }

    /**
     * 保存ViewType-View
     * 如果viewTypeCount == 1 则表示该列表只有一种布局，不需要保存
     */
    fun putConvertView(viewType: Int, convertView: View) {
        if (viewTypeCount <= 1) {
            return
        }
        mCreatedView.put(viewType, convertView)
    }

    /**
     * 返回布局文件ID
     */
    abstract fun getLayoutId(viewType: Int): Int

    /**
     * 显示数据
     */
    abstract fun convert(holder: CommonViewHolder, data: T, position: Int)
}