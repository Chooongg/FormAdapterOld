package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        item: FormItem,
        boundary: Boundary
    ) {
        with(holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)) {
            val builder = shapeAppearanceModel.toBuilder()
            if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                if (boundary.top != 0 && boundary.start != 0) {
                    builder.setTopLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setTopLeftCornerSize(0f)
                if (boundary.top != 0 && boundary.end != 0) {
                    builder.setTopRightCornerSize(dp2px(8f).toFloat())
                } else builder.setTopRightCornerSize(0f)
                if (boundary.bottom != 0 && boundary.start != 0) {
                    builder.setBottomLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomLeftCornerSize(0f)
                if (boundary.bottom != 0 && boundary.end != 0) {
                    builder.setBottomRightCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomRightCornerSize(0f)
            } else {
                if (boundary.top != 0 && boundary.end != 0) {
                    builder.setTopLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setTopLeftCornerSize(0f)
                if (boundary.top != 0 && boundary.start != 0) {
                    builder.setTopRightCornerSize(dp2px(8f).toFloat())
                } else builder.setTopRightCornerSize(0f)
                if (boundary.bottom != 0 && boundary.end != 0) {
                    builder.setBottomLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomLeftCornerSize(0f)
                if (boundary.bottom != 0 && boundary.start != 0) {
                    builder.setBottomRightCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomRightCornerSize(0f)
            }
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
        item: FormGroupTitle,
        boundary: Boundary
    ) {
        with(holder.getView<TextView>(R.id.formContent)) {
            text = item.name
        }
    }
}