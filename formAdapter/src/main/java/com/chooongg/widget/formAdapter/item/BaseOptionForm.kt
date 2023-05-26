package com.chooongg.widget.formAdapter.item

import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.enum.FormOptionLoadMode
import com.chooongg.widget.formAdapter.option.Option
import com.chooongg.widget.formAdapter.option.OptionResultState
import kotlinx.coroutines.launch

abstract class BaseOptionForm(name: CharSequence?, field: String?) : BaseForm(name, field) {

    /**
     * 是否具有打开操作
     */
    protected open var hasOpenOperation = true

    /**
     * 选项加载模式
     */
    open var optionLoadMode = FormOptionLoadMode.EMPTY

    /**
     * 选项
     */
    var options: List<Option>? = null

    /**
     * 选项加载回调
     */
    var optionLoader: (suspend ((OptionResultState) -> Unit) -> Unit)? = null

    override fun onBeforeBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {

    }

    protected fun loadOption(adapter: FormPartAdapter, result: (OptionResultState) -> Unit) {
        if (optionLoader != null) {
            adapter.adapterScope.launch {
                optionLoader!!.invoke(result)
            }
        } else {
            adapter.adapterScope.launch {
                adapter.globalAdapter.listener?.onFormOptionNeedLoad(
                    adapter, this@BaseOptionForm, result
                )
            }
        }
    }
}