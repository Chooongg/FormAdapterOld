package com.chooongg.widget.formAdapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.creator.FormCreator
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.style.NoneStyle
import com.chooongg.widget.formAdapter.style.Style
import java.lang.ref.WeakReference

class FormAdapter(isEditable: Boolean = false) : BaseFormAdapter(isEditable) {

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    fun bind(recyclerView: RecyclerView, listener: FormEventListener? = null) {
        _recyclerView = WeakReference(recyclerView)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 120).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val pair = adapter.getWrappedAdapterAndPosition(position)
                    val adapter = pair.first
                    return if (adapter is FormPartAdapter) {
                        val item = adapter.getItem(pair.second)
                        item.spanSize
                    } else {
                        120
                    }
                }
            }
        }
        this.listener = listener
        recyclerView.adapter = adapter
        for (i in recyclerView.itemDecorationCount - 1 downTo 0) {
            recyclerView.removeItemDecorationAt(i)
        }
        recyclerView.addItemDecoration(FormGridItemDecoration(recyclerView.context, this))
    }

    fun setNewInstance(block: FormCreator.() -> Unit) {
        clear()
        val creator = FormCreator().apply(block)
        creator.parts.forEach {
            adapter.addAdapter(FormPartAdapter(this, it.first))
        }
        creator.parts.forEachIndexed { index, pair ->
            (adapter.adapters[index] as FormPartAdapter).submitList(pair.second)
        }
    }

    fun addPart(style: Style = NoneStyle, block: PartCreator.() -> Unit) {
        val part = FormPartAdapter(this, style)
        adapter.addAdapter(part)
        part.submitList(block)
    }

    fun update() {
        adapter.adapters.forEach {
            if (it is FormPartAdapter) it.update()
        }
    }

    fun scrollToItem(item: BaseForm) {
        _recyclerView?.get()?.smoothScrollToPosition(item.adapterPosition)
    }

    fun clear() {
        adapter.adapters.forEach { adapter.removeAdapter(it) }
        _recyclerView?.get()?.recycledViewPool?.clear()
    }
}