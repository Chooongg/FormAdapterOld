package com.chooongg.widget.formAdapter

import android.view.View
import com.chooongg.widget.formAdapter.item.FormItem

interface FormEventListener {

    /**
     * 表单点击事件
     */
    fun onFormClick(adapter: FormPartAdapter, item: FormItem, itemView: View) {}

    /**
     * 表单菜单点击事件
     */
    fun onFormMenuClick(adapter: FormPartAdapter, item: FormItem, itemView: View, menuView: View) {}

    /**
     * 表单内容更改事件
     */
    fun onFormContentChanged(manager: FormAdapter, item: FormItem, itemView: View) {}
}