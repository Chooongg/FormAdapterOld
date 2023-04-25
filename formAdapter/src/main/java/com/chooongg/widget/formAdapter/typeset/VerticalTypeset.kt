package com.chooongg.widget.formAdapter.typeset

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.widget.formAdapter.FormManager
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.enum.FormEmsMode
import com.chooongg.widget.formAdapter.item.FormItem

class VerticalTypeset(
    ems: Int = FormManager.defaultEmsSize,
    emsMode: FormEmsMode = FormEmsMode.MIN
) : Typeset(ems, emsMode) {

    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LinearLayoutCompat(parent.context).apply {
            orientation = LinearLayoutCompat.VERTICAL
            addView(AppCompatTextView(context))
        }
    }

    override fun onBindItemTypesetParent(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormItem
    ) {
        holder.getView<AppCompatTextView>(R.id.formInternalNameTextView).text = item.name
    }

    override fun addContentView(parent: ViewGroup, view: View) {
        parent.addView(view)
    }
}