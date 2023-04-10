package com.chooongg.widget.formAdapter

import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.style.Style
import com.chooongg.widget.formAdapter.typeset.Typeset

/**
 * 项目类型
 */
internal data class ItemViewType(
    val style: Class<out Style>,
    val typeset: Class<out Typeset>?,
    val item: Class<out FormItem>
)