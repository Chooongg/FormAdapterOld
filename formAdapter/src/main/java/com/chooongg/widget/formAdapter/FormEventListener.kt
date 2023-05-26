package com.chooongg.widget.formAdapter

import android.view.View
import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.item.BaseOptionForm
import com.chooongg.widget.formAdapter.option.Option
import com.chooongg.widget.formAdapter.option.OptionResultState

interface FormEventListener {

    /**
     * 表单项目点击时
     */
    fun onFormClick(adapter: FormPartAdapter, item: BaseForm, itemView: View) {}

    /**
     * 表单菜单点击时
     */
    fun onFormMenuClick(adapter: FormPartAdapter, item: BaseForm, itemView: View, menuView: View) {}

    /**
     * 表单内容更改时
     */
    fun onFormContentChanged(adapter: FormPartAdapter, item: BaseForm, itemView: View) {}

    /**
     * 表单选项需要加载时
     */
    suspend fun onFormOptionNeedLoad(
        adapter: FormPartAdapter,
        item: BaseForm,
        block: (OptionResultState) -> Unit
    ) {
    }
}