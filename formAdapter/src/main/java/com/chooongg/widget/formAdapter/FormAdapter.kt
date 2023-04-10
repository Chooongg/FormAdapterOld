package com.chooongg.widget.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.style.NoneStyle
import com.chooongg.widget.formAdapter.style.Style
import com.google.android.flexbox.FlexboxLayoutManager
import java.lang.ref.WeakReference

class FormAdapter(isEditable: Boolean = false) : BaseFormAdapter(isEditable) {

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    fun bind(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
        recyclerView.layoutManager = object : FlexboxLayoutManager(recyclerView.context) {
            override fun generateDefaultLayoutParams() = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { flexGrow = 1f }
        }
        recyclerView.adapter = adapter
    }

    fun addPart(style: Style = NoneStyle, block: PartCreator.() -> Unit) {
        adapter.addAdapter(FormPartAdapter(this, style).apply {
            submitList(block)
        })
    }
}