package com.chooongg.widget.formAdapter.item

import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder
import com.chooongg.widget.formAdapter.enum.FormOptionLoadMode
import com.chooongg.widget.formAdapter.option.Option
import kotlinx.coroutines.launch

abstract class BaseOptionForm(name: CharSequence?, field: String?) : BaseForm(name, field) {

    protected sealed class OptionState {
        object NotLoading : OptionState()
        object Loading : OptionState()
        class Error : OptionState()
    }

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
    var optionLoaded: (((List<Option>?) -> Unit) -> Unit)? = null

    override fun onBeforeBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) {

    }

    protected fun loadOption(adapter: FormPartAdapter, result: (List<Option>?) -> Unit) {
        if (optionLoaded != null) {
            adapter.adapterScope.launch {
                optionLoaded!!.invoke(result)
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