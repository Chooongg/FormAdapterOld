package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.divider.MaterialDivider

class FormDivider : BaseForm(null, null) {

    override var isNeedToTypeset: Boolean = false

    override var isShowOnEdge: Boolean = false

    var insetStart = -1

    var insetEnd = -1

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View =
        MaterialDivider(parent.context).apply {
            id = R.id.formContent
            layoutParams = MarginLayoutParams(-1, -2)
        }

    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        with(holder.getView<MaterialDivider>(R.id.formContent)) {
            dividerInsetStart = if (insetStart == -1) {
                when (boundary.start) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }
            } else insetStart
            dividerInsetEnd = if (insetEnd == -1) {
                when (boundary.end) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }
            } else insetEnd
            updateLayoutParams<MarginLayoutParams> {
                topMargin = holder.paddingVerticalLocal
                bottomMargin = holder.paddingVerticalLocal
            }
        }
    }
}