package com.chooongg.widget.formAdapter

import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.partStyle.PartStyle
import com.chooongg.widget.formAdapter.typeset.Typeset

/**
 * 项目类型
 */
internal data class ItemViewType(
    val partStyle: Class<out PartStyle>,

    val typeset: Typeset?,
    val item: Class<out FormItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemViewType) return false

        if (partStyle != other.partStyle) return false
        if (typeset != null && other.typeset != null) {
            if (typeset::class != other.typeset::class) return false
        } else if (typeset == null && other.typeset != null) return false
        else if (typeset != null) return false
        if (item != other.item) return false

        return true
    }

    override fun hashCode(): Int {
        var result = partStyle.hashCode()
        if (typeset != null) result = 31 * result + typeset::class.hashCode()
        result = 31 * result + item.hashCode()
        return result
    }
}
