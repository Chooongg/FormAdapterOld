package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.typeset.Typeset

/**
 * 表单项根样式
 */
abstract class Style(
    /**
     * 默认的排版方式
     */
    val defaultTypeset: Typeset
) {

    /**
     * 是否需要装饰边距
     */
    open var isNeedDecorationMargins = true
        protected set

    /**
     * 创建项目的样式布局
     * @param parent Item的父布局
     * @return 返回样式布局, 如果不需要创建则返回 null
     */
    abstract fun onCreateItemParent(parent: ViewGroup): ViewGroup?

    abstract fun getStyleParentLayout(holder: FormViewHolder): View

    /**
     * 绑定项目的样式布局
     */
    abstract fun onBindItemParentLayout(holder: FormViewHolder, item: BaseForm)

    /**
     * 创建分组标题
     */
    abstract fun onCreateGroupTitle(parent: ViewGroup): View

    /**
     * 绑定分组标题
     */
    abstract fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle)
}