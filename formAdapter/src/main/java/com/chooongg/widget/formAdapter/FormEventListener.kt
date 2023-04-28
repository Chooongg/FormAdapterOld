package com.chooongg.widget.formAdapter

import android.view.View
import com.chooongg.widget.formAdapter.item.BaseForm

interface FormEventListener {

    /**
     * 表单点击事件
     */
    fun onFormClick(adapter: FormPartAdapter, item: BaseForm, itemView: View) {}

    /**
     * 表单菜单点击事件
     */
    fun onFormMenuClick(adapter: FormPartAdapter, item: BaseForm, itemView: View, menuView: View) {}

    /**
     * 表单内容更改事件
     */
    fun onFormContentChanged(adapter: FormPartAdapter, item: BaseForm, itemView: View) {}
}