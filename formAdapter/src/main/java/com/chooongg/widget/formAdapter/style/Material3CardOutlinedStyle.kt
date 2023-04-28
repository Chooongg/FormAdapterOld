package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.typeset.FlexBoxTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel

class Material3CardOutlinedStyle(
    defaultTypeset: Typeset = FlexBoxTypeset()
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
                        tag = shapeAppearanceModel.toBuilder().build()
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

    override fun getStyleParentLayout(holder: FormViewHolder): View {
        return holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)
    }

    override fun onBindItemParentLayout(holder: FormViewHolder, item: BaseForm) {
        with((holder.itemView as FrameLayout).getChildAt(0) as MaterialCardView) {
            val originalShape = tag as? ShapeAppearanceModel ?: return
            val builder = shapeAppearanceModel.toBuilder()
            if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopLeftCornerSize(originalShape.topLeftCornerSize)
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopRightCornerSize(originalShape.topRightCornerSize)
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomLeftCornerSize(originalShape.bottomLeftCornerSize)
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomRightCornerSize(originalShape.bottomRightCornerSize)
                } else builder.setBottomRightCornerSize(0f)
            } else {
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopLeftCornerSize(originalShape.topLeftCornerSize)
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopRightCornerSize(originalShape.topRightCornerSize)
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomLeftCornerSize(originalShape.bottomLeftCornerSize)
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomRightCornerSize(originalShape.bottomRightCornerSize)
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
            context, com.google.android.material.R.style.TextAppearance_Material3_TitleMedium
        )
    }

    override fun onBindGroupTitle(holder: FormViewHolder, item: FormGroupTitle) {
        with(holder.getView<TextView>(R.id.formContent)) {
            text = item.name ?: resources.getString(R.string.formNone)
            setPaddingRelative(
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
}