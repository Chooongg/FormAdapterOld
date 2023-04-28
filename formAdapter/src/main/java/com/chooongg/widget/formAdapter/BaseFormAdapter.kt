package com.chooongg.widget.formAdapter

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.style.Style
import com.chooongg.widget.formAdapter.typeset.Typeset
import java.lang.ref.WeakReference

abstract class BaseFormAdapter(isEditable: Boolean = false) {

    companion object {
        const val NOTIFY_PAYLOADS = "update"
    }

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            if (adapter.itemCount > 0) adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }

    internal val adapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    internal val itemTypePool = ArrayList<TypeInfo>()

    var listener: FormEventListener? = null
        internal set

    internal fun indexPartOf(part: FormPartAdapter) = adapter.adapters.indexOf(part)

    fun updateItem(item: BaseForm) {
        if (item.adapterPosition != -1) {
            adapter.notifyItemChanged(item.adapterPosition, NOTIFY_PAYLOADS)
        }
    }

    internal fun getItemViewType(style: Style, typeset: Typeset, item: BaseForm): Int {
        val info = TypeInfo(style.javaClass, typeset, item)
        if (!itemTypePool.contains(info)) {
            itemTypePool.add(info)
        }
        return itemTypePool.indexOf(info)
    }

    internal fun findItemViewTypeInfo(itemType: Int) = itemTypePool[itemType]

    fun clearFocus(){
        _recyclerView?.get()?.focusedChild?.clearFocus()
    }
}