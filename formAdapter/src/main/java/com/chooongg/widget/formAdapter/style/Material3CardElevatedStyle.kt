package com.chooongg.widget.formAdapter.style

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.item.FormGroupTitle
import com.chooongg.widget.formAdapter.typeset.HorizontalTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel

class Material3CardElevatedStyle(
    defaultTypeset: Typeset = HorizontalTypeset()
) : Style(defaultTypeset) {

    override fun onCreateItemParent(parent: ViewGroup): ViewGroup {
        return MaterialCardView(
            parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
        ).apply {
            id = R.id.formInternalStyleLayout
            tag = shapeAppearanceModel.toBuilder().build()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                outlineSpotShadowColor = Color.TRANSPARENT
                outlineAmbientShadowColor = Color.TRANSPARENT
            }
        }
    }

    override fun getStyleParentLayout(holder: FormViewHolder): View {
        return holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)
    }

    override fun onBindItemParentLayout(holder: FormViewHolder, item: BaseForm) {
        with(holder.getView<MaterialCardView>(R.id.formInternalStyleLayout)) {
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