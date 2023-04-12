package com.chooongg.widget.formAdapter.creator

import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.item.GroupForm

class GroupCreator {

    internal val groupList = mutableListOf<FormItem>()

    fun add(item: FormItem) {
        groupList.add(item)
    }

    fun addSingleLine(block: GroupCreator.() -> Unit) {
        val creator = GroupCreator().apply(block)
        creator.groupList.forEach {
            if (it is GroupForm) throw IllegalArgumentException("GroupForm can not be added to SingleLine")
        }
        groupList.add(GroupForm().apply {
            items = ArrayList(creator.groupList)
        })
    }
}