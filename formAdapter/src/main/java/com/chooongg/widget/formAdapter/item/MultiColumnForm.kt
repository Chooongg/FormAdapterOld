package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder

class MultiColumnForm : FormItem("", null) {

    internal var items = ArrayList<FormItem>()

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View {
        return View(parent.context)
    }

    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) = Unit
}