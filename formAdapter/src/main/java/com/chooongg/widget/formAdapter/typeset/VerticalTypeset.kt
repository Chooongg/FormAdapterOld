package com.chooongg.widget.formAdapter.typeset

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormItem

object VerticalTypeset : Typeset {
    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LinearLayoutCompat(parent.context).apply {
            orientation = LinearLayoutCompat.VERTICAL
            addView(AppCompatTextView(context))
        }
    }

    override fun onBindItemTypesetParent(holder: FormViewHolder, item: FormItem) {
        holder.getView<AppCompatTextView>(R.id.formInternalNameTextView).text = item.name
    }
}