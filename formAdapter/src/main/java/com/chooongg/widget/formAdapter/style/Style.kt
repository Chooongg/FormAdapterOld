package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.Typeset

abstract class Style(val defaultTypeset: Typeset?) {

    /**
     * 创建项目的样式布局
     * @param parent Item的父布局
     * @return 返回样式布局, 如果没有创建则返回项目父布局
     */
    abstract fun onCreateItemParent(parent: ViewGroup): ViewGroup

    /**
     * 绑定项目的样式布局
     * @param parent Item的父布局
     * @return 返回样式布局, 如果没有创建则返回项目父布局
     */
    abstract fun onBindItemParentLayout(parent: View, item: FormItem): ViewGroup
}