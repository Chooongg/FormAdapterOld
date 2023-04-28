package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder

class FormGroupTitle internal constructor(name: CharSequence?, field: String?) :
    BaseForm(name, field) {

    override var isNeedToTypeset: Boolean = false

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View =
        adapter.style.onCreateGroupTitle(parent)

    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        adapter.style.onBindGroupTitle(holder, this)
    }

    override fun onBindContentView(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        payloads: MutableList<Any>?
    ) {
        adapter.style.onBindGroupTitle(holder, this)
    }
}