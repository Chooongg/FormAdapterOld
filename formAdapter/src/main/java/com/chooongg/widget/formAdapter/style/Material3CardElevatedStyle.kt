package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.use
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.typeset.HorizontalTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset
import com.google.android.material.card.MaterialCardView

class Material3CardElevatedStyle(
    defaultTypeset: Typeset = HorizontalTypeset()
) : Style(defaultTypeset) {

    override fun onCreateItemParent(parent: ViewGroup): ViewGroup {
        return MaterialCardView(
            parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
        ).apply { id = R.id.formInternalStyleLayout }
    }

    override fun getStyleParentLayout(holder: FormViewHolder): View {
        return holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)
    }

    override fun onBindItemParentLayout(holder: FormViewHolder, item: BaseForm) {
        val shapeCornerSize = with(holder.itemView.context) {
            obtainStyledAttributes(
                obtainStyledAttributes(
                    intArrayOf(com.google.android.material.R.attr.materialCardViewElevatedStyle)
                ).use { it.getResourceId(0, 0) },
                intArrayOf(com.google.android.material.R.attr.shapeAppearance)
            ).use { it.getResourceId(0, 0) }.let { resId ->
                obtainStyledAttributes(
                    resId, intArrayOf(com.google.android.material.R.attr.cornerSize)
                ).use { it.getDimensionPixelSize(0, 0) }
            }
        }
        with(holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)) {
            val builder = shapeAppearanceModel.toBuilder()
            if (layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopLeftCornerSize(shapeCornerSize.toFloat())
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopRightCornerSize(shapeCornerSize.toFloat())
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomLeftCornerSize(shapeCornerSize.toFloat())
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomRightCornerSize(shapeCornerSize.toFloat())
                } else builder.setBottomRightCornerSize(0f)
            } else {
                if (item.boundary.top != 0 && item.boundary.end != 0) {
                    builder.setTopLeftCornerSize(shapeCornerSize.toFloat())
                } else builder.setTopLeftCornerSize(0f)
                if (item.boundary.top != 0 && item.boundary.start != 0) {
                    builder.setTopRightCornerSize(shapeCornerSize.toFloat())
                } else builder.setTopRightCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.end != 0) {
                    builder.setBottomLeftCornerSize(shapeCornerSize.toFloat())
                } else builder.setBottomLeftCornerSize(0f)
                if (item.boundary.bottom != 0 && item.boundary.start != 0) {
                    builder.setBottomRightCornerSize(shapeCornerSize.toFloat())
                } else builder.setBottomRightCornerSize(0f)
            }
            shapeAppearanceModel = builder.build()
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