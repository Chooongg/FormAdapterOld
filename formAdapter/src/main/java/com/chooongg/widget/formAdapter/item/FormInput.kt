package com.chooongg.widget.formAdapter.item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R

class FormInput(name: CharSequence?, field: String?) : BaseForm(name, field) {

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(R.layout.form_item_input, parent, false)


    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
    }
}