package com.chooongg.widget.formAdapter.style

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.use
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.FlexBoxTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel

class Material3CardElevatedStyle(
    defaultTypeset: Typeset = FlexBoxTypeset()
) : Style(defaultTypeset) {

    private lateinit var shape: ShapeAppearanceModel

    override fun onCreateItemParent(parent: ViewGroup): ViewGroup {
        return MaterialCardView(
            parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
        ).apply { id = R.id.formInternalStyleLayout }
    }

    override fun getStyleParentLayout(holder: FormViewHolder): View {
        return holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)
    }

    override fun onBindItemParentLayout(holder: FormViewHolder, item: FormItem) {
        if (!this::shape.isInitialized) {
            val shapeAppearanceId = with(TypedValue()) {
                holder.itemView.context.theme.resolveAttribute(
                    com.google.android.material.R.attr.materialCardViewElevatedStyle, this, true
                )
                holder.itemView.context.obtainStyledAttributes(
                    resourceId,
                    intArrayOf(com.google.android.material.R.attr.shapeAppearance)
                ).use {
                    it.getResourceId(0, 0)
                }
            }
            shape =
                ShapeAppearanceModel.builder(holder.itemView.context, shapeAppearanceId, 0).build()
        }
        with(holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)) {
            val builder = shapeAppearanceModel.toBuilder()
            if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopLeftCorner(shape.topLeftCorner)
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopRightCorner(shape.topRightCorner)
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomLeftCorner(shape.bottomLeftCorner)
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomRightCorner(shape.bottomRightCorner)
                } else builder.setBottomRightCornerSize(0f)
            } else {
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopLeftCorner(shape.topLeftCorner)
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopRightCorner(shape.topRightCorner)
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomLeftCorner(shape.bottomLeftCorner)
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomRightCorner(shape.bottomRightCorner)

                } else builder.setBottomRightCornerSize(0f)
            }
            shapeAppearanceModel = builder.build()
        }
    }

    @Suppress("DEPRECATION")
    override fun onCreateGroupTitle(parent: ViewGroup) = TextView(parent.context).apply {
        id = R.id.formContent
        setTextAppearance(
            context, com.google.android.material.R.style.TextAppearance_Material3_HeadlineSmall
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