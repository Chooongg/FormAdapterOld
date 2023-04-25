package com.chooongg.widget.formAdapter

import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.style.Style
import com.chooongg.widget.formAdapter.typeset.Typeset

/**
 * 项目类型
 */
internal class TypeInfo(
    val style: Class<Style>,
    val typeset: Typeset,
    val item: FormItem
) {

    override fun toString(): String {
        return "${style.simpleName} ${typeset.javaClass.simpleName} ${item.javaClass.simpleName}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TypeInfo) return false

        if (style != other.style) return false
        if (typeset != other.typeset) return false
        if (item.javaClass != other.item.javaClass) return false

        return true
    }

    override fun hashCode(): Int {
        var result = style.hashCode()
        result = 31 * result + typeset.hashCode()
        result = 31 * result + item::javaClass.hashCode()
        return result
    }
}