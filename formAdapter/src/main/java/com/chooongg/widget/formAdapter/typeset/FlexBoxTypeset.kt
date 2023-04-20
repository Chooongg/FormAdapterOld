package com.chooongg.widget.formAdapter.typeset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormItem
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

object FlexBoxTypeset : Typeset {

    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.form_typeset_flexbox, parent, false) as FlexboxLayout
    }

    override fun onBindItemTypesetParent(holder: FormViewHolder, item: FormItem) {
        with(holder.getView<FlexboxLayout>(R.id.formInternalTypesetLayout)) {
            updatePaddingRelative(
                when (item.boundary.start) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.top) {
                    Boundary.NONE -> 0
                    else -> holder.paddingVerticalGlobal - holder.paddingVerticalLocal
                }, when (item.boundary.end) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.bottom) {
                    Boundary.NONE -> 0
                    else -> holder.paddingVerticalGlobal - holder.paddingVerticalLocal
                }
            )
        }
        with(holder.getView<MaterialTextView>(R.id.formInternalNameTextView)) {
            text = item.name
            text = "1 2 3 4 5 6 7 8 9 0"
        }
        with(holder.getView<MaterialButton>(R.id.formInternalMenuButton)) {
            if (item.menuText != null || item.menuIconRes != null) {
                text = item.menuText
                if (item.menuIconRes != null) setIconResource(item.menuIconRes!!) else icon = null
                iconSize = item.menuIconSize
                visibility = View.VISIBLE
            } else visibility = View.GONE
        }
    }

    override fun generateDefaultLayoutParams() = FlexboxLayout.LayoutParams(-2, -2).apply {
        flexGrow = 1000f
    }
}