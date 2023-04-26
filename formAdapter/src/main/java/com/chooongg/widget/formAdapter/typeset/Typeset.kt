package com.chooongg.widget.formAdapter.typeset

import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.GravityInt
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.Boundary
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.enum.FormEmsMode
import com.chooongg.widget.formAdapter.item.BaseForm
import com.google.android.material.button.MaterialButton

abstract class Typeset(val ems: Int, val emsMode: FormEmsMode) {

    @GravityInt
    open fun contentGravity(): Int = Gravity.NO_GRAVITY

    /**
     * 创建项目的排版布局
     * @param parent 父布局
     * @return 返回排版布局, 如果不需要创建则返回 null
     */
    abstract fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup?

    open fun onBindItemTypesetParentPadding(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.getViewOrNull<LinearLayoutCompat>(R.id.formInternalTypesetLayout)?.apply {
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

    /**
     * 绑定项目的排版布局
     */
    abstract fun onBindItemTypesetParent(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    )

    /**
     * 添加内容布局
     */
    abstract fun addContentView(parent: ViewGroup, view: View)

    open fun onCreateMenuButton(parent: ViewGroup) {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.form_typeset_default_menu, parent, false)
        val paddingVerticalLocal =
            view.resources.getDimensionPixelOffset(R.dimen.formVerticalLocalPaddingSize)
        val paddingHorizontalLocal =
            view.resources.getDimensionPixelOffset(R.dimen.formHorizontalLocalPaddingSize)
        val insetHorizontal =
            (2f * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        view.setPadding(
            paddingHorizontalLocal + insetHorizontal,
            paddingVerticalLocal,
            paddingHorizontalLocal + insetHorizontal,
            paddingVerticalLocal
        )
        parent.addView(
            view, MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

    open fun onBindMenuButton(adapter: FormPartAdapter, holder: FormViewHolder, item: BaseForm) {
        with(holder.getView<MaterialButton>(R.id.formInternalMenuButton)) {
            if (!item.typesetIgnoreMenuButtons() && (item.menuText != null || item.menuIconRes != null)) {
                text = item.menuText
                if (item.menuIconRes != null) setIconResource(item.menuIconRes!!) else icon = null
                iconSize =
                    item.menuIconSize ?: resources.getDimensionPixelSize(R.dimen.formTextSize)
                updateLayoutParams<MarginLayoutParams> {
                    marginEnd = -holder.paddingHorizontalLocal
                    topMargin = -holder.paddingVerticalLocal
                    bottomMargin = -holder.paddingVerticalLocal
                }
                setOnClickListener {
                    adapter.globalAdapter.listener?.onFormMenuClick(adapter, item, holder.itemView, this)
                }
                visibility = View.VISIBLE
            } else {
                setOnClickListener(null)
                visibility = View.GONE
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Typeset) return false

        if (ems != other.ems) return false
        if (emsMode != other.emsMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ems
        result = 31 * result + emsMode.hashCode()
        return result
    }
}