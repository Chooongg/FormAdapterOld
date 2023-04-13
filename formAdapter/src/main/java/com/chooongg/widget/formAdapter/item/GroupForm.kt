package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder

class GroupForm : FormItem("", null) {

    internal var items = ArrayList<FormItem>()

    override fun onCreateItemView(adapter: FormPartAdapter, parent: ViewGroup): View {
        return View(parent.context)
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        boundary: Boundary
    ) = Unit
}