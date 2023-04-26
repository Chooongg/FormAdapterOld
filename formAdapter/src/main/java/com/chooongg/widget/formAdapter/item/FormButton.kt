package com.chooongg.widget.formAdapter.item

import android.animation.AnimatorInflater
import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButton.IconGravity
import com.google.android.material.theme.overlay.MaterialThemeOverlay

class FormButton(name: CharSequence?, field: String?) : BaseForm(name, field) {

    enum class ButtonStyle {
        DEFAULT, TEXT, TONAL, OUTLINED, ELEVATED, UN_ELEVATED
    }

    override var isNeedToTypeset: Boolean = false

    var buttonStyle: ButtonStyle = ButtonStyle.DEFAULT

    var iconRes: Int? = null

    var iconSize: Int? = null

    @IconGravity
    var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    var iconTint: (Context.() -> ColorStateList)? = null

    @Deprecated("invalid property", ReplaceWith("name"))
    override var menuText: CharSequence? = null

    @Deprecated("invalid property", ReplaceWith("name"))
    override var menuIconRes: Int? = null

    @Deprecated("invalid property", ReplaceWith("name"))
    override var menuIconSize: Int? = null

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View {
        return MaterialButton(parent.context).apply {
            id = R.id.formContent
            layoutParams = MarginLayoutParams(-1, -2)
        }
    }

    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        with(holder.getView<MaterialButton>(R.id.formContent)) {
            isEnabled = isRealEnable(adapter.globalAdapter.isEditable)
            text = name
            hint = this@FormButton.hint ?: resources.getString(R.string.formNone)
            if (iconRes != null) setIconResource(iconRes!!) else icon = null
            updateMarginsBasedOnBoundary(holder, this)

            val textSize = resources.getDimensionPixelSize(R.dimen.formTextSize)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            iconSize = this@FormButton.iconSize ?: textSize
            iconGravity = this@FormButton.iconGravity
            val style = when (buttonStyle) {
                ButtonStyle.DEFAULT -> context.obtainStyledAttributes(
                    intArrayOf(com.google.android.material.R.attr.materialButtonStyle)
                ).use { it.getResourceId(0, 0) }

                ButtonStyle.TEXT -> context.obtainStyledAttributes(
                    intArrayOf(com.google.android.material.R.attr.borderlessButtonStyle)
                ).use { it.getResourceId(0, 0) }

                ButtonStyle.TONAL ->
                    com.google.android.material.R.style.Widget_Material3_Button_TonalButton

                ButtonStyle.OUTLINED -> context.obtainStyledAttributes(
                    intArrayOf(com.google.android.material.R.attr.materialButtonOutlinedStyle)
                ).use { it.getResourceId(0, 0) }

                ButtonStyle.ELEVATED ->
                    com.google.android.material.R.style.Widget_Material3_Button_ElevatedButton

                ButtonStyle.UN_ELEVATED ->
                    com.google.android.material.R.style.Widget_Material3_Button_UnelevatedButton
            }
            val wrap = MaterialThemeOverlay.wrap(context, null, 0, style)
            setTextColor(wrap.obtainStyledAttributes(
                style, intArrayOf(android.R.attr.textColor)
            ).use { it.getColorStateList(0) })
            iconTint = if (this@FormButton.iconTint == null) {
                wrap.obtainStyledAttributes(
                    style, intArrayOf(androidx.appcompat.R.attr.iconTint)
                ).use { it.getColorStateList(0) }
            } else this@FormButton.iconTint!!.invoke(context)
            backgroundTintList = wrap.obtainStyledAttributes(
                style, intArrayOf(androidx.appcompat.R.attr.backgroundTint)
            ).use { it.getColorStateList(0) }
            strokeColor = wrap.obtainStyledAttributes(
                style, intArrayOf(com.google.android.material.R.attr.strokeColor)
            ).use { it.getColorStateList(0) }
            strokeWidth = wrap.obtainStyledAttributes(
                style, intArrayOf(com.google.android.material.R.attr.strokeWidth)
            ).use { it.getDimensionPixelSize(0, 0) }
            rippleColor = wrap.obtainStyledAttributes(
                style, intArrayOf(com.google.android.material.R.attr.rippleColor)
            ).use { it.getColorStateList(0) }
            elevation = wrap.obtainStyledAttributes(
                style, intArrayOf(com.google.android.material.R.attr.strokeWidth)
            ).use { it.getDimension(0, 0f) }

            val stateListId = wrap.obtainStyledAttributes(
                style, intArrayOf(android.R.attr.stateListAnimator)
            ).use { it.getResourceId(0, 0) }
            stateListAnimator = if (stateListId != 0) {
                AnimatorInflater.loadStateListAnimator(wrap, stateListId)
            } else null
        }
    }

    override fun setItemClick(adapter: FormPartAdapter, holder: FormViewHolder) {
        holder.getView<MaterialButton>(R.id.formContent).setOnClickListener {
            adapter.globalAdapter.listener?.onFormClick(adapter, this, it)
        }
    }

    private fun updateMarginsBasedOnBoundary(holder: FormViewHolder, view: MaterialButton) {
        view.updateLayoutParams<MarginLayoutParams> {
            marginStart = when (boundary.start) {
                Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                else -> holder.paddingHorizontalLocal
            }
            topMargin = when (boundary.top) {
                Boundary.GLOBAL, Boundary.LOCAL -> holder.paddingHorizontalGlobal
                else -> holder.paddingHorizontalLocal
            } - view.insetTop
            marginEnd = when (boundary.end) {
                Boundary.GLOBAL -> holder.paddingHorizontalGlobal
                else -> holder.paddingHorizontalLocal
            }
            bottomMargin = when (boundary.bottom) {
                Boundary.GLOBAL, Boundary.LOCAL -> holder.paddingHorizontalGlobal
                else -> holder.paddingHorizontalLocal
            } - view.insetBottom
        }
    }
}