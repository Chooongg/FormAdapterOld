package com.chooongg.widget.formAdapter.typeset

import android.view.ViewGroup
import com.chooongg.widget.formAdapter.item.FormItem

interface Typeset {

    /**
     * 创建项目的排版布局
     * @param parent 父布局
     * @return 返回排版布局
     */
    fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup

    /**
     * 绑定项目的排版布局
     * @param parent 排版布局
     */
    fun onBindItemTypesetParent(parent: ViewGroup, item: FormItem)
}