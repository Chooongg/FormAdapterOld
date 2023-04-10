package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.HorizontalTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset
import com.google.android.flexbox.FlexboxLayoutManager

object NoneStyle : Style() {

    override var defaultTypeset: Typeset? = HorizontalTypeset
    override fun onCreateItemParent(parent: ViewGroup): ViewGroup? = null
    override fun onBindItemParentLayout(holder: FormViewHolder, item: FormItem) = Unit

    @Suppress("DEPRECATION")
    override fun onCreateGroupTitle(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            id = R.id.formContent
            setTextAppearance(
                context,
                com.google.android.material.R.style.TextAppearance_Material3_HeadlineMedium
            )
        }
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle) {
        with(holder.getView<TextView>(R.id.formContent)) {
            text = item.name
        }
    }
}