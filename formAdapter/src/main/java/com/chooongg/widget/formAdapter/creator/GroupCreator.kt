package com.chooongg.widget.formAdapter.creator

import com.chooongg.widget.formAdapter.item.BaseForm
import com.chooongg.widget.formAdapter.item.MultiColumnForm
import com.chooongg.widget.formAdapter.typeset.FlexBoxTypeset

open class GroupCreator {

    internal val items = mutableListOf<BaseForm>()

    fun add(item: BaseForm) {
        items.add(item)
    }

    fun addMultiColumn(block: MultiColumnCreator.() -> Unit) {
        val creator = MultiColumnCreator().apply(block)
        items.add(MultiColumnForm().apply {
            maxColumn = creator.maxColumn
            creator.items.forEach {
                if (it is MultiColumnForm) throw IllegalArgumentException("GroupForm can not be added to SingleLine")
                it.isShowOnEdge = creator.isShowOnEdge
                if (it.typeset == null) it.typeset = typeset ?: FlexBoxTypeset()
            }
            items = ArrayList(creator.items)
        })
    }
}