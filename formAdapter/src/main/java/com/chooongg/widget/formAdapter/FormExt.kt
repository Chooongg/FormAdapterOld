package com.chooongg.widget.formAdapter

import com.chooongg.widget.formAdapter.creator.GroupCreator
import com.chooongg.widget.formAdapter.item.FormDivider
import com.chooongg.widget.formAdapter.item.FormText

/**
 * 添加分割线
 */
fun GroupCreator.addDivider(
    block: FormDivider.() -> Unit = {}
) = add(FormDivider().apply(block))

/**
 * 添加文本
 */
fun GroupCreator.addText(
    name: CharSequence?,
    field: String? = null,
    block: FormText.() -> Unit = {}
) = add(FormText(name, field).apply(block))