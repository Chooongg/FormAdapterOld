package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder

class FormGroupTitle(name: CharSequence, field: String?) : FormItem(name, field) {

    override var isNeedToTypeset: Boolean = false

    override fun onCreateItemView(adapter: FormPartAdapter, parent: ViewGroup): View {
        return adapter.style.onCreateGroupTitle(parent)
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        holder: FormViewHolder
    ) {
        adapter.style.onBindGroupTitle(holder, this)
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        payloads: MutableList<Any>?
    ) {
        adapter.style.onBindGroupTitle(holder, this)
    }
}