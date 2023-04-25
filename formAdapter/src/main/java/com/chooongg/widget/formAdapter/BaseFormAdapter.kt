package com.chooongg.widget.formAdapter

import android.util.Log
import androidx.collection.ArraySet
import androidx.recyclerview.widget.ConcatAdapter
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.style.Style
import com.chooongg.widget.formAdapter.typeset.Typeset

abstract class BaseFormAdapter(isEditable: Boolean = false) {

    companion object {
        const val NOTIFY_PAYLOADS = "update"
    }

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            if (adapter.itemCount > 0) adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }

    internal val adapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    internal val itemTypePool = ArrayList<TypeInfo>()

    internal fun indexPartOf(part: FormPartAdapter) = adapter.adapters.indexOf(part)

    fun updateItem(item: FormItem) {
        if (item.adapterPosition != -1) {
            adapter.notifyItemChanged(item.adapterPosition, NOTIFY_PAYLOADS)
        }
    }

    internal fun getItemViewType(style: Style, typeset: Typeset, item: FormItem): Int {
        val info = TypeInfo(style.javaClass, typeset, item)
        if (!itemTypePool.contains(info)) {
            itemTypePool.add(info)
        }
        return itemTypePool.indexOf(info)
    }

    internal fun findItemViewTypeInfo(itemType: Int) = itemTypePool[itemType]
}