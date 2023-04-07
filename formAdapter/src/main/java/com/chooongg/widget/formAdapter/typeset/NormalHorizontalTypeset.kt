package com.chooongg.widget.formAdapter.typeset

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.widget.formAdapter.item.FormItem

object NormalHorizontalTypeset : Typeset {
    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LinearLayoutCompat(parent.context).apply {
            orientation = LinearLayoutCompat.HORIZONTAL
            addView(AppCompatTextView(context))
        }
    }

    override fun onBindItemTypesetParent(parent: ViewGroup, item: FormItem) {
        (parent.getChildAt(0) as AppCompatTextView).text = item.name
    }
}