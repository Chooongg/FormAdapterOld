package com.chooongg.widget.formAdapter.typeset

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.item.FormItem

/**
 * 无排版
 */
object NoneTypeset : Typeset {
    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup? = null
    override fun onBindItemTypesetParent(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormItem
    ) = Unit

    override fun addContentView(parent: ViewGroup, view: View) {}
}