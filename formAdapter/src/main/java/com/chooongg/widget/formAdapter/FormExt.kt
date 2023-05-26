package com.chooongg.widget.formAdapter

import com.chooongg.widget.formAdapter.creator.GroupCreator
import com.chooongg.widget.formAdapter.item.FormButton
import com.chooongg.widget.formAdapter.item.FormDivider
import com.chooongg.widget.formAdapter.item.FormInput
import com.chooongg.widget.formAdapter.item.FormSelector
import com.chooongg.widget.formAdapter.item.FormText

/**
 * 添加按钮
 */
fun GroupCreator.addButton(
    name: CharSequence?, field: String? = null, block: FormButton.() -> Unit = {}
) = add(FormButton(name, field).apply(block))

/**
 * 添加分割线
 */
fun GroupCreator.addDivider(
    block: FormDivider.() -> Unit = {}
) = add(FormDivider().apply(block))

/**
 * 添加输入框
 */
fun GroupCreator.addInput(
    name: CharSequence?, field: String? = null, block: FormInput.() -> Unit = {}
) = add(FormInput(name, field).apply(block))

/**
 * 添加选择器
 */
fun GroupCreator.addSelector(
    name: CharSequence?, field: String? = null, block: FormSelector.() -> Unit = {}
) = add(FormSelector(name, field).apply(block))

/**
 * 添加文本
 */
fun GroupCreator.addText(
    name: CharSequence?, field: String? = null, block: FormText.() -> Unit = {}
) = add(FormText(name, field).apply(block))
