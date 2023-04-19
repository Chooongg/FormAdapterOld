package com.chooongg.widget.formAdapter.typeset

import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.GravityInt
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.item.FormItem

interface Typeset {

    @GravityInt
    fun contentGravity(): Int = Gravity.CENTER_VERTICAL or Gravity.START

    /**
     * 创建项目的排版布局
     * @param parent 父布局
     * @return 返回排版布局, 如果不需要创建则返回 null
     */
    fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup?

    /**
     * 绑定项目的排版布局
     */
    fun onBindItemTypesetParent(holder: FormViewHolder, item: FormItem)

    fun generateDefaultLayoutParams(): ViewGroup.LayoutParams? = null
}