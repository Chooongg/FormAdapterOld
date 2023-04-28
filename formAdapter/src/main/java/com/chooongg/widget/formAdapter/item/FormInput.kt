package com.chooongg.widget.formAdapter.item

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import androidx.annotation.IntRange
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.R
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.min

class FormInput(name: CharSequence?, field: String?) : BaseForm(name, field) {

    /**
     * 前缀文本
     */
    var prefixText: CharSequence? = null

    /**
     * 后缀文本
     */
    var suffixText: CharSequence? = null

    /**
     * 最大长度
     */
    @IntRange(from = 1)
    var maxLength: Int = Int.MAX_VALUE

    /**
     * 最大行数
     */
    @IntRange(from = 1)
    var maxLines: Int = Int.MAX_VALUE

    /**
     * 单行
     */
    fun singleLine() {
        maxLines = 1
    }

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(R.layout.form_item_input, parent, false)

    @SuppressLint("RestrictedApi", "PrivateResource")
    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        holder.getView<TextInputLayout>(R.id.formContent).also {
            it.isEnabled = isRealEnable(adapter.globalAdapter.isEditable)
            it.endIconMode = TextInputLayout.END_ICON_NONE
            it.prefixText = prefixText
            it.suffixText = suffixText
            if (menuIconRes != null) {
                it.endIconMode = TextInputLayout.END_ICON_CUSTOM
                it.setEndIconDrawable(menuIconRes!!)
                it.setEndIconOnClickListener { view ->
                    adapter.globalAdapter.listener?.onFormMenuClick(
                        adapter, this, holder.itemView, view
                    )
                }
            } else if (isRealEnable(adapter.globalAdapter.isEditable)) {
                it.endIconMode = TextInputLayout.END_ICON_CUSTOM
                it.setEndIconDrawable(R.drawable.form_ic_clear)
                it.setEndIconOnClickListener { _ -> it.editText?.text = null }
            } else {
                it.endIconMode = TextInputLayout.END_ICON_NONE
            }

            val textSize = it.resources.getDimensionPixelSize(R.dimen.formTextSize)
            val insetHorizontal = (2f * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
            with(it.findViewById<CheckableImageButton>(com.google.android.material.R.id.text_input_start_icon)) {
                background = ResourcesCompat.getDrawable(
                    resources, R.drawable.form_input_icon_background, context.theme
                ).apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this is RippleDrawable) {
                        radius = min(
                            textSize + holder.paddingHorizontalLocal * 2 + insetHorizontal * 2,
                            textSize + holder.paddingVerticalLocal * 2 + insetHorizontal * 2
                        ) / 2
                    }
                }
                updateLayoutParams<MarginLayoutParams> {
                    width = textSize + holder.paddingHorizontalLocal * 2 + insetHorizontal * 2
                    height = textSize + holder.paddingVerticalLocal * 2 + insetHorizontal * 2
                }
            }
            with(it.findViewById<CheckableImageButton>(com.google.android.material.R.id.text_input_end_icon)) {
                background = ResourcesCompat.getDrawable(
                    resources, R.drawable.form_input_icon_background, context.theme
                ).apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this is RippleDrawable) {
                        radius = min(
                            textSize + holder.paddingHorizontalLocal * 2 + insetHorizontal * 2,
                            textSize + holder.paddingVerticalLocal * 2 + insetHorizontal * 2
                        ) / 2
                    }
                }
                updateLayoutParams<MarginLayoutParams> {
                    width = textSize + holder.paddingHorizontalLocal * 2 + insetHorizontal * 2
                    height = textSize + holder.paddingVerticalLocal * 2 + insetHorizontal * 2
                }
            }
            it.updateLayoutParams<MarginLayoutParams> {
                topMargin = -holder.paddingVerticalLocal
                bottomMargin = -holder.paddingVerticalLocal
                leftMargin = -holder.paddingHorizontalLocal
                rightMargin = -holder.paddingHorizontalLocal
            }
        }
        holder.getView<TextInputEditText>(R.id.formContentEditText).also {
            if (it.tag is TextWatcher) it.removeTextChangedListener(it.tag as TextWatcher)
            it.maxLines = maxLines
            if (maxLines == 1) {
                it.setSingleLine()
            } else {
                it.maxLines = maxLines
            }
            it.filters = arrayOf(LengthFilter(maxLength))
            it.hint = hint ?: it.resources.getString(R.string.formInputHint)
            it.setText(content?.toString())
            val watcher = it.doAfterTextChanged { editable ->
                content = editable
                adapter.globalAdapter.listener?.onFormContentChanged(adapter, this, it)
            }
            it.tag = watcher
            it.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    adapter.globalAdapter.clearFocus()
                    true
                } else false
            }
        }
    }

    override fun typesetIgnoreMenuButtons() = true
}