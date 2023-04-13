package com.chooongg.widget.formAdapter.creator

import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.item.GroupForm

open class GroupCreator {

    internal val items = mutableListOf<FormItem>()

    fun add(item: FormItem) {
        items.add(item)
    }

    fun addSingleLine(block: GroupFormCreator.() -> Unit) {
        val creator = GroupFormCreator().apply(block)
        creator.items.forEachIndexed { index, formItem ->
            if (index >= 5) throw IndexOutOfBoundsException("SingleLine can not have more than 5 items")
            if (formItem is GroupForm) throw IllegalArgumentException("GroupForm can not be added to SingleLine")
            formItem.isShowOnEdge = creator.isShowOnEdge
        }
        items.add(GroupForm().apply {
            items = ArrayList(creator.items)
        })
    }
}