package com.chooongg.widget.formAdapter

import androidx.collection.ArraySet
import androidx.recyclerview.widget.ConcatAdapter
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.partStyle.PartStyle
import com.chooongg.widget.formAdapter.typeset.Typeset

abstract class BaseFormAdapter(isEditable: Boolean = false) {

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            if (adapter.itemCount > 0) adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }

    internal val adapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    internal val itemTypeLookup = ArraySet<ItemViewType>()

    internal fun getItemViewType(partStyle: PartStyle, typeset: Typeset?, item: FormItem): Int {
        val itemType = ItemViewType(partStyle, typeset, item::class.java)
        if (!itemTypeLookup.contains(itemType)) {
            itemTypeLookup.add(itemType)
            return itemTypeLookup.size - 1
        }
        return itemTypeLookup.indexOf(itemType)
    }

    internal fun findItemViewTypeForInt(itemType: Int) = itemTypeLookup.valueAt(itemType)

    fun clear() {
        adapter.adapters.forEach { adapter.removeAdapter(it) }
        itemTypeLookup.clear()
    }
}