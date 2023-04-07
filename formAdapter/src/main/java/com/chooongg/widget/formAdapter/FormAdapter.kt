package com.chooongg.widget.formAdapter

import androidx.collection.ArraySet
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.style.Style
import com.chooongg.widget.formAdapter.typeset.Typeset
import java.lang.ref.WeakReference

class FormAdapter(isEditable: Boolean = false, spanSize: Int) {

    private var _recyclerView: WeakReference<RecyclerView>? = null

    private val adapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            if (adapter.itemCount > 0) adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }

    var spanSize: Int = spanSize
        set(value) {
            if (field != value) {
                field = value
                _recyclerView?.get()?.let {
                    it.layoutManager = GridLayoutManager(it.context, spanSize)
                }
            }
        }

    private val itemTypeLookup = ArraySet<ItemViewType>()

    fun bind(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, spanSize)
        recyclerView.adapter = adapter
    }

    internal fun getItemViewType(style: Style, typeset: Typeset?, item: FormItem): Int {
        val itemType = ItemViewType(style::class.java, typeset, item::class.java)
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