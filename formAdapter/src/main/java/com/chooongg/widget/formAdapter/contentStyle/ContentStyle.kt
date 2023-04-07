package com.chooongg.widget.formAdapter.contentStyle

import android.view.ViewGroup
import com.chooongg.widget.formAdapter.item.FormItem

interface ContentStyle {

    /**
     * 创建项目的内容布局
     * @param parent 父布局
     * @return 返回内容布局
     */
    fun onCreateItemContentParent(parent: ViewGroup): ViewGroup

    /**
     * 绑定项目的内容布局
     * @param parent 内容布局
     */
    fun onBindItemContentParent(parent: ViewGroup, item: FormItem)
}