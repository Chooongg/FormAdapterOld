package com.chooongg.widget.formAdapter

import androidx.recyclerview.widget.ConcatAdapter
import com.chooongg.widget.formAdapter.item.FormItem

abstract class BaseFormAdapter(isEditable: Boolean = false) {

    companion object {
        const val NOTIFY_PAYLOADS = "update"
    }

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            if (adapter.itemCount > 0) adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }

    internal val adapter = ConcatAdapter()

    internal fun indexPartOf(part: FormPartAdapter) = adapter.adapters.indexOf(part)

    fun clear() {
        adapter.adapters.forEach { adapter.removeAdapter(it) }
    }

    fun updateItem(item: FormItem) {
        if (item.adapterPosition != -1) {
            adapter.notifyItemChanged(item.adapterPosition, NOTIFY_PAYLOADS)
        }
    }
}