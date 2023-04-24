package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.FlexBoxTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset
import com.google.android.material.card.MaterialCardView

class Material3CardOutlinedStyle(
    defaultTypeset: Typeset? = FlexBoxTypeset
) : Style(defaultTypeset) {

    override fun onCreateItemParent(parent: ViewGroup): ViewGroup {
        return object : FrameLayout(parent.context) {
            init {
                addView(
                    MaterialCardView(
                        context,
                        null,
                        com.google.android.material.R.attr.materialCardViewOutlinedStyle
                    ).apply {
                        id = R.id.formInternalStyleLayout
                    }, LayoutParams(-1, -2)
                )
            }

            override fun addView(child: View?) {
                if (child is MaterialCardView) {
                    super.addView(child)
                } else (getChildAt(0) as ViewGroup).addView(child)
            }
        }
    }

    override fun onBindItemParentLayout(holder: FormViewHolder, item: FormItem) {
        with((holder.itemView as FrameLayout).getChildAt(0) as MaterialCardView) {
            val builder = shapeAppearanceModel.toBuilder()
            if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopRightCornerSize(dp2px(8f).toFloat())
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomRightCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomRightCornerSize(0f)
            } else {
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopRightCornerSize(dp2px(8f).toFloat())
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomLeftCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomRightCornerSize(dp2px(8f).toFloat())
                } else builder.setBottomRightCornerSize(0f)
            }
            shapeAppearanceModel = builder.build()
            setPaddingRelative(
                if (item.boundary.start == Boundary.GLOBAL) 0 else strokeWidth,
                if (item.boundary.top != Boundary.NONE) 0 else strokeWidth,
                if (item.boundary.end == Boundary.GLOBAL) 0 else strokeWidth,
                if (item.boundary.bottom != Boundary.NONE) 0 else strokeWidth
            )
            updateLayoutParams<FrameLayout.LayoutParams> {
                marginStart = if (item.boundary.start == Boundary.GLOBAL) 0 else -strokeWidth
                topMargin = if (item.boundary.top != Boundary.NONE) 0 else -strokeWidth
                marginEnd = if (item.boundary.end == Boundary.GLOBAL) 0 else -strokeWidth
                bottomMargin = if (item.boundary.bottom != Boundary.NONE) 0 else -strokeWidth
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreateGroupTitle(parent: ViewGroup) = TextView(parent.context).apply {
        id = R.id.formContent
        setTextAppearance(
            context, com.google.android.material.R.style.TextAppearance_Material3_HeadlineMedium
        )
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle) {
        with(holder.getView<TextView>(R.id.formContent)) {
            text = item.name ?: resources.getString(R.string.formNone)
        }
    }
}