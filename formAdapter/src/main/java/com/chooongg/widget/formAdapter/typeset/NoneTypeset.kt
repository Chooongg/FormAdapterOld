package com.chooongg.widget.formAdapter.typeset

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePaddingRelative
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormManager
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.enum.FormEmsMode
import com.chooongg.widget.formAdapter.item.BaseForm

/**
 * 无排版
 */
object NoneTypeset : Typeset(FormManager.defaultEmsSize, FormEmsMode.NONE) {

    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup? = null
    override fun onBindItemTypesetParentPadding(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        adapter.style.getStyleParentLayout(holder).apply {
            if (this !is ViewGroup) return@apply
            updatePaddingRelative(
                when (item.boundary.start) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.top) {
                    Boundary.GLOBAL, Boundary.LOCAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.end) {
                    Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }, when (item.boundary.bottom) {
                    Boundary.GLOBAL, Boundary.LOCAL -> holder.paddingHorizontalGlobal
                    else -> holder.paddingHorizontalLocal
                }
            )
        }
    }

    override fun onBindItemTypesetParent(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = Unit

    override fun addContentView(parent: ViewGroup, view: View) {}

    override fun onCreateMenuButton(parent: ViewGroup) = Unit

    override fun onBindMenuButton(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) = Unit
}