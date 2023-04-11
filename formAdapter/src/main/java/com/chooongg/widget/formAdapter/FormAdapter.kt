package com.chooongg.widget.formAdapter

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.creator.PartCreator
import com.chooongg.widget.formAdapter.style.NoneStyle
import com.chooongg.widget.formAdapter.style.Style
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.lang.ref.WeakReference

class FormAdapter(isEditable: Boolean = false) : BaseFormAdapter(isEditable) {

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    fun bind(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
        recyclerView.layoutManager = FlexboxLayoutManager(recyclerView.context).apply {
            justifyContent = JustifyContent.CENTER
        }
        recyclerView.adapter = adapter
    }

    fun addPart(style: Style = NoneStyle, block: PartCreator.() -> Unit) {
        adapter.addAdapter(FormPartAdapter(this, style).apply {
            submitList(block)
        })
    }

    private fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}