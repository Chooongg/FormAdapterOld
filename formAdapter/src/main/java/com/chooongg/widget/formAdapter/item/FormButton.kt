package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.button.MaterialButton

class FormButton(name: CharSequence, field: String?) : FormItem(name, field) {

    override var isNeedToTypeset: Boolean = false

    override fun onCreateItemView(adapter: FormPartAdapter, parent: ViewGroup): View {
        return MaterialButton(parent.context).apply {
            id = R.id.formContent
        }
    }

    override fun onBindItemView(adapter: FormPartAdapter, holder: FormViewHolder) {
        with(holder.getView<MaterialButton>(R.id.formContent)) {
            text = name
        }
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        payloads: MutableList<Any>?
    ) {

    }
}