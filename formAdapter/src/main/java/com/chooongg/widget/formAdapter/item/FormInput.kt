package com.chooongg.widget.formAdapter.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.textfield.TextInputLayout

class FormInput(name: CharSequence?, field: String?) : BaseForm(name, field) {

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(R.layout.form_item_input, parent, false)


    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        holder.getView<TextInputLayout>(R.id.formContent).also {
            it.isEnabled = isRealEnable(adapter.globalAdapter.isEditable)
            it.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            it.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = -holder.paddingVerticalLocal
                bottomMargin = -holder.paddingVerticalLocal
                leftMargin = -holder.paddingHorizontalLocal
                rightMargin = -holder.paddingHorizontalLocal
            }
        }
    }

    override fun typesetIgnoreMenuButtons() = true
}