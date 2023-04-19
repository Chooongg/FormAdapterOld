package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.textview.MaterialTextView

class FormText(name: CharSequence, field: String?) : FormItem(name, field) {

    override fun onCreateItemView(adapter: FormPartAdapter, parent: ViewGroup): View {
        return MaterialTextView(parent.context).apply {
            id = R.id.formContent
        }
    }

    override fun onBindItemView(adapter: FormPartAdapter, holder: FormViewHolder) {
        holder.getView<MaterialTextView>(R.id.formContent).also {
            it.text = content?.toString()
            it.hint = hint ?: "空"
        }
    }
}