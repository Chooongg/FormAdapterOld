package com.chooongg.widget.formAdapter

import androidx.collection.ArraySet
import androidx.recyclerview.widget.ConcatAdapter
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.style.Style
import com.chooongg.widget.formAdapter.typeset.Typeset

abstract class BaseFormAdapter(isEditable: Boolean = false) {

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            if (adapter.itemCount > 0) adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }

    internal val adapter = ConcatAdapter()

    internal val itemTypeLookup = ArraySet<ItemViewType>()

    internal fun getItemViewType(style: Style, typeset: Typeset?, item: FormItem): Int {
        val itemType = ItemViewType(
            if (typeset != null) typeset::class.java else null,
            item::class.java
        )
        if (!itemTypeLookup.contains(itemType)) {
            itemTypeLookup.add(itemType)
            return itemTypeLookup.size - 1
        }
        return itemTypeLookup.indexOf(itemType)
    }

    internal fun findItemViewTypeForInt(itemType: Int) = itemTypeLookup.valueAt(itemType)

    internal fun indexPartOf(part:FormPartAdapter) = adapter.adapters.indexOf(part)

    fun clear() {
        adapter.adapters.forEach { adapter.removeAdapter(it) }
        itemTypeLookup.clear()
    }
}