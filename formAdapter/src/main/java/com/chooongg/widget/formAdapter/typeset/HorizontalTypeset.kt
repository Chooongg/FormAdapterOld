package com.chooongg.widget.formAdapter.typeset

import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormItem
import com.google.android.material.textview.MaterialTextView

object HorizontalTypeset : Typeset {

    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return object : LinearLayoutCompat(parent.context) {
            override fun generateDefaultLayoutParams() = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }.apply {
            orientation = LinearLayoutCompat.HORIZONTAL
            addView(
                MaterialTextView(context).apply {
                    id = R.id.formInternalNameTextView
                    setEms(4)
                }, LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

    override fun onBindItemTypesetParent(holder: FormViewHolder, item: FormItem) {
        holder.getView<MaterialTextView>(R.id.formInternalNameTextView).text = item.name
    }
}