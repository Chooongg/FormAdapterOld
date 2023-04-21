package com.chooongg.widget.formAdapter.item

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.textview.MaterialTextView

class FormText(name: CharSequence, field: String?) : FormItem(name, field) {

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.form_item_text, parent, false)
        return view
    }

    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        holder.getView<MaterialTextView>(R.id.formContent).also {
            it.text = content?.toString()
            it.hint = hint ?: "空"
        }
    }
}