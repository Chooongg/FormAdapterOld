package com.chooongg.widget.formAdapter.typeset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updatePaddingRelative
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormItem
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.textview.MaterialTextView

object FlexBoxTypeset : Typeset {


    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.form_typeset_flexbox, parent, false) as LinearLayoutCompat
    }

    override fun onBindItemTypesetParent(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormItem
    ) {
        with(holder.getView<LinearLayoutCompat>(R.id.formInternalTypesetLayout)) {
            updatePaddingRelative(
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
        with(holder.getView<MaterialTextView>(R.id.formInternalNameTextView)) {
            text = item.name
        }
    }

    override fun addContentView(parent: ViewGroup, view: View) {
        parent.findViewById<FlexboxLayout>(R.id.formInternalTypesetFlexBoxLayout).apply {
            addView(view, FlexboxLayout.LayoutParams(-2, -2).apply { flexGrow = Float.MAX_VALUE })
        }
    }
}