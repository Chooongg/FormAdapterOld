package com.chooongg.widget.formAdapter.typeset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.updatePaddingRelative
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormItem
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.textview.MaterialTextView

object FlexBoxTypeset : Typeset {
    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.form_typeset_flexbox, parent, false) as FlexboxLayout
    }

    override fun onBindItemTypesetParent(holder: FormViewHolder, item: FormItem) {
        val layout = holder.getView<FlexboxLayout>(R.id.formInternalTypesetLayout)
        with(holder.getView<MaterialTextView>(R.id.formInternalNameTextView)) {
            ContextThemeWrapper(context, com.google.android.material.R.style.Widget_Material3_Button_ElevatedButton)
            text = item.name
            updatePaddingRelative(
                start = when (item.boundary.start) {
                    Boundary.GLOBAL -> resources.getDimensionPixelOffset(R.dimen.formHorizontalGlobalPaddingSize)
                    else -> resources.getDimensionPixelOffset(R.dimen.formHorizontalLocalPaddingSize)
                }
            )
        }
    }

    override fun generateDefaultLayoutParams() = FlexboxLayout.LayoutParams(-2, -2).apply {
        flexGrow = 1f
    }
}