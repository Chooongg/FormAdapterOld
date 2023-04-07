package com.chooongg.widget.formAdapter.partStyle

import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.Typeset

/**
 * 表单项根样式
 */
abstract class PartStyle(
    /**
     * 默认排版
     */
) {

    /**
     * 默认排版
     */
    open var defaultTypeset: Typeset? = null
        protected set

    /**
     * 创建项目的样式布局
     * @param parent Item的父布局
     * @return 返回样式布局, 如果不需要创建则返回 null
     */
    abstract fun onCreateItemParent(parent: ViewGroup): ViewGroup?

    /**
     * 绑定项目的样式布局
     * @param parent Item的父布局
     */
    abstract fun onBindItemParentLayout(holder: FormViewHolder, item: FormItem)
}