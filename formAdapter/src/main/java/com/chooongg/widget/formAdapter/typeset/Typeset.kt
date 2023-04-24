package com.chooongg.widget.formAdapter.typeset

import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.GravityInt
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.item.FormItem
import com.google.android.material.button.MaterialButton

interface Typeset {

    @GravityInt
    fun contentGravity(): Int = Gravity.NO_GRAVITY

    /**
     * 创建项目的排版布局
     * @param parent 父布局
     * @return 返回排版布局, 如果不需要创建则返回 null
     */
    fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup?

    /**
     * 绑定项目的排版布局
     */
    fun onBindItemTypesetParent(adapter: FormPartAdapter, holder: FormViewHolder, item: FormItem)

    /**
     * 添加内容布局
     */
    fun addContentView(parent: ViewGroup, view: View)

    fun onCreateMenuButton(parent: ViewGroup) {
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

    fun onBindMenuButton(adapter: FormPartAdapter, holder: FormViewHolder, item: FormItem) {
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
                    adapter.listener?.onFormMenuClick(adapter, item, holder.itemView, this)
                }
                visibility = View.VISIBLE
            } else {
                setOnClickListener(null)
                visibility = View.GONE
            }
        }
    }
}