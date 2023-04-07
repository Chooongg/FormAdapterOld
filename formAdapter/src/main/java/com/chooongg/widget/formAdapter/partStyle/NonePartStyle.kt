package com.chooongg.widget.formAdapter.partStyle

import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.HorizontalTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset

object NonePartStyle : PartStyle() {
    override var defaultTypeset: Typeset? = HorizontalTypeset()
    override fun onCreateItemParent(parent: ViewGroup): ViewGroup? = null
    override fun onBindItemParentLayout(holder: FormViewHolder, item: FormItem) = Unit
}