package com.chooongg.widget.formAdapter.style

import android.view.ViewGroup
import android.widget.TextView
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.HorizontalTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset

object NoneStyle : Style() {

    override var defaultTypeset: Typeset? = HorizontalTypeset
    override fun onCreateItemParent(parent: ViewGroup): ViewGroup? = null
    override fun onBindItemParentLayout(
        holder: FormViewHolder,
        item: FormItem,
        boundary: Boundary
    ) = Unit

    @Suppress("DEPRECATION")
    override fun onCreateGroupTitle(parent: ViewGroup) = TextView(parent.context).apply {
        id = R.id.formContent
        setTextAppearance(
            context,
            com.google.android.material.R.style.TextAppearance_Material3_HeadlineMedium
        )
    }

    override fun onBindGroupTitle(
        holder: FormViewHolder,
        item: FormGroupTitle,
        boundary: Boundary
    ) {
        with(holder.getView<TextView>(R.id.formContent)) {
            text = item.name
        }
    }
}