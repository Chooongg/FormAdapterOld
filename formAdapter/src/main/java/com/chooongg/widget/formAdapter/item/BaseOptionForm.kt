package com.chooongg.widget.formAdapter.item

import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.option.Option

abstract class BaseOptionForm(name: CharSequence?, field: String?) : BaseForm(name, field) {

    var options: List<Option>? = null

    var optionLoaded: (((List<Option>?) -> Unit) -> Unit)? = null

    override fun onBeforeBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {
        optionLoaded = {
            it.invoke(options)
        }
    }
}