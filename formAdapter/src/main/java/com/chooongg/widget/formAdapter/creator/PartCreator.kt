package com.chooongg.widget.formAdapter.creator

import com.chooongg.widget.formAdapter.item.BaseForm

class PartCreator {

    internal val groups = mutableListOf<MutableList<out BaseForm>>()

    /**
     * 片段名称
     */
    var partName: CharSequence? = null

    /**
     * 片段字段
     */
    var partField: String? = null

    /**
     * 是否是动态片段
     */
    var dynamicPart: Boolean = false

    /**
     * 动态片段删除确认
     */
    var dynamicPartDeleteConfirm: Boolean = true

    /**
     * 动态片段最小组数量
     */
    @androidx.annotation.IntRange(from = 0)
    var dynamicPartMinGroupCount: Int = 1

    /**
     * 动态片段最大组数量
     */
    @androidx.annotation.IntRange(from = 1)
    var dynamicPartMaxGroupCount: Int = Int.MAX_VALUE

    internal var dynamicPartCreateGroupBlock: (GroupCreator.() -> Unit)? = null

    internal var dynamicPartNameFormatBlock: ((name: CharSequence?, index: Int) -> CharSequence)? =
        null

    /**
     * 添加组
     */
    fun createGroup(block: GroupCreator.() -> Unit) {
        groups.add(GroupCreator().apply(block).items)
    }

    /**
     * 动态片段添加组
     */
    fun dynamicPartCreateGroupListener(block: (GroupCreator.() -> Unit)?) {
        dynamicPartCreateGroupBlock = block
        if (block != null) {
            dynamicPart = true
        }
    }

    /**
     * 动态片段名称格式化
     */
    fun dynamicPartNameFormatListener(block: ((name: CharSequence?, index: Int) -> CharSequence)?) {
        dynamicPartNameFormatBlock = block
    }
}