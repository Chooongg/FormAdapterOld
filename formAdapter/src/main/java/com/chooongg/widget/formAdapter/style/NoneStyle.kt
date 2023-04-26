package com.chooongg.widget.formAdapter.style

import android.view.ViewGroup
import android.widget.TextView
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.typeset.FlexBoxTypeset

object NoneStyle : Style(FlexBoxTypeset()) {

    override var isNeedDecorationMargins = false
    override fun onCreateItemParent(parent: ViewGroup): ViewGroup? = null
    override fun getStyleParentLayout(holder: FormViewHolder) = holder.itemView

    override fun onBindItemParentLayout(holder: FormViewHolder, item: BaseForm) = Unit

    @Suppress("DEPRECATION")
    override fun onCreateGroupTitle(parent: ViewGroup) = TextView(parent.context).apply {
        id = R.id.formContent
        setTextAppearance(
            context,
            com.google.android.material.R.style.TextAppearance_Material3_TitleMedium
        )
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle) {
        with(holder.getView<TextView>(R.id.formContent)) {
            text = item.name
            setPaddingRelative(
                when (item.boundary.start) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.top) {
                    Boundary.GLOBAL, Boundary.LOCAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.end) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.bottom) {
                    Boundary.GLOBAL, Boundary.LOCAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }
            )
        }
    }
}