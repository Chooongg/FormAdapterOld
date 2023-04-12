package com.chooongg.widget.formAdapter

import android.content.res.Resources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.style.NoneStyle
import com.chooongg.widget.formAdapter.style.Style
import java.lang.ref.WeakReference

class FormAdapter(isEditable: Boolean = false) : BaseFormAdapter(isEditable) {

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    fun bind(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 120).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val pair = adapter.getWrappedAdapterAndPosition(position)
                    val adapter = pair.first
                    return if (adapter is FormPartAdapter) {
                        val item = adapter.getItem(pair.second)
                        120
                    } else 120
                }
            }
        }
        recyclerView.adapter = adapter
        recyclerView.setPaddingRelative(30, 0, 30, 0)
    }

    fun addPart(style: Style = NoneStyle, block: PartCreator.() -> Unit) {
        adapter.addAdapter(FormPartAdapter(this, style).apply {
            submitList(block)
        })
    }

    private fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}