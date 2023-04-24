package com.chooongg.widget.formAdapter.creator

import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.item.MultiColumnForm

open class GroupCreator {

    internal val items = mutableListOf<FormItem>()

    fun add(item: FormItem) {
        items.add(item)
    }

    fun addMultiColumn(block: MultiColumnCreator.() -> Unit) {
        val creator = MultiColumnCreator().apply(block)
        creator.items.forEach {
            if (it is MultiColumnForm) throw IllegalArgumentException("GroupForm can not be added to SingleLine")
            it.isShowOnEdge = creator.isShowOnEdge
        }
        items.add(MultiColumnForm().apply {
            maxColumn = creator.maxColumn
            items = ArrayList(creator.items)
        })
    }
}