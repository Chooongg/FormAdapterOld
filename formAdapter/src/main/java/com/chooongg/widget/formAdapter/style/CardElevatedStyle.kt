package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.HorizontalTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset
import com.google.android.material.card.MaterialCardView

object CardElevatedStyle : Style() {

    override var defaultTypeset: Typeset? = HorizontalTypeset

    override fun onCreateItemParent(parent: ViewGroup): ViewGroup {
        return MaterialCardView(
            parent.context,
            null,
            com.google.android.material.R.attr.materialCardViewElevatedStyle
        ).apply {
            id = R.id.formInternalStyleLayout
        }
    }

    override fun onBindItemParentLayout(
        holder: FormViewHolder,
        item: FormItem
    ) {
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder, boundary: Boundary) {
        holder.itemView.updateLayoutParams<RecyclerView.LayoutParams> {
            topMargin = when (boundary.top) {
                Boundary.LOCAL -> dp2px(8f)
                Boundary.GLOBAL -> dp2px(16f)
                else -> 0
            }
            if (holder.itemView.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                marginEnd = when (boundary.left) {
                    Boundary.LOCAL -> dp2px(8f)
                    Boundary.GLOBAL -> dp2px(16f)
                    else -> 0
                }
                marginStart = when (boundary.right) {
                    Boundary.LOCAL -> dp2px(8f)
                    Boundary.GLOBAL -> dp2px(16f)
                    else -> 0
                }
            } else {
                marginStart = when (boundary.left) {
                    Boundary.LOCAL -> dp2px(8f)
                    Boundary.GLOBAL -> dp2px(16f)
                    else -> 0
                }
                marginEnd = when (boundary.right) {
                    Boundary.LOCAL -> dp2px(8f)
                    Boundary.GLOBAL -> dp2px(16f)
                    else -> 0
                }
            }
            bottomMargin = when (boundary.bottom) {
                Boundary.LOCAL -> dp2px(8f)
                Boundary.GLOBAL -> dp2px(16f)
                else -> 0
            }
        }
        with(holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)) {
            val builder = shapeAppearanceModel.toBuilder()
            if (boundary.top != 0 && boundary.left != 0) {
                builder.setTopLeftCornerSize(dp2px(8f).toFloat())
            } else builder.setTopLeftCornerSize(0f)
            if (boundary.top != 0 && boundary.right != 0) {
                builder.setTopRightCornerSize(dp2px(8f).toFloat())
            } else builder.setTopRightCornerSize(0f)
            if (boundary.bottom != 0 && boundary.left != 0) {
                builder.setBottomLeftCornerSize(dp2px(8f).toFloat())
            } else builder.setBottomLeftCornerSize(0f)
            if (boundary.bottom != 0 && boundary.right != 0) {
                builder.setBottomRightCornerSize(dp2px(8f).toFloat())
            } else builder.setBottomRightCornerSize(0f)
            shapeAppearanceModel = builder.build()
        }
    }

    override fun onCreateGroupTitle(parent: ViewGroup) = TextView(parent.context).apply {
        id = R.id.formContent
        setTextAppearance(
            context,
            com.google.android.material.R.style.TextAppearance_Material3_HeadlineMedium
        )
    }

    override fun onBindGroupTitle(
        holder: FormViewHolder,
        item: FormGroupTitle
    ) {
        with(holder.getView<TextView>(R.id.formContent)) {
            text = item.name
        }
    }
}