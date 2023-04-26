package com.chooongg.widget.formAdapter.typeset

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.widget.formAdapter.FormManager
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.chooongg.widget.formAdapter.enum.FormEmsMode
import com.chooongg.widget.formAdapter.item.BaseForm

class VerticalTypeset(
    ems: Int = FormManager.defaultEmsSize,
    emsMode: FormEmsMode = FormEmsMode.MIN
) : Typeset(ems, emsMode) {

    override fun onCreateItemTypesetParent(parent: ViewGroup): ViewGroup {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.form_typeset_vertical, parent, false) as LinearLayoutCompat
    }

    override fun onBindItemTypesetParent(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.getView<AppCompatTextView>(R.id.formInternalNameTextView).text = item.name
    }

    override fun addContentView(parent: ViewGroup, view: View) {
        parent.addView(view)
    }

    override fun onCreateMenuButton(parent: ViewGroup) {
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
        (parent.getChildAt(0) as ViewGroup).addView(
            view, ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }
}