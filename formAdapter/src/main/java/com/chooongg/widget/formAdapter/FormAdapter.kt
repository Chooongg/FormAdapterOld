package com.chooongg.widget.formAdapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class FormAdapter(isEditable: Boolean = false, spanSize: Int = 1) : BaseFormAdapter(isEditable) {

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    var spanSize: Int = spanSize
        set(value) {
            if (field != value) {
                field = value
                _recyclerView?.get()?.let {
                    it.layoutManager = GridLayoutManager(it.context, spanSize)
                }
            }
        }

    fun bind(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, spanSize)
        recyclerView.adapter = adapter
    }
}