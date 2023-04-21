package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.google.android.material.divider.MaterialDivider

class FormDivider(name: CharSequence, field: String?) : FormItem(name, field) {

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View {
        return MaterialDivider(parent.context)
    }

    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
    }
}