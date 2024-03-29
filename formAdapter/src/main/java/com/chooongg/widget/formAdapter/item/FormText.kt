package com.chooongg.widget.formAdapter.item

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.textview.MaterialTextView

class FormText(name: CharSequence?, field: String?) : BaseForm(name, field) {

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(R.layout.form_item_text, parent, false)


    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        holder.getView<MaterialTextView>(R.id.formContent).also {
            it.gravity = if (isNeedToTypeset) {
                (typeset ?: adapter.style.defaultTypeset).contentGravity()
            } else Gravity.NO_GRAVITY
            it.text = getContentText()
            it.hint = hint ?: it.resources.getString(R.string.formNone)
            it.updateLayoutParams<MarginLayoutParams> {
                topMargin = -holder.paddingVerticalLocal
                bottomMargin = -holder.paddingVerticalLocal
                leftMargin = -holder.paddingHorizontalLocal
                rightMargin = -holder.paddingHorizontalLocal
            }
        }
    }
}