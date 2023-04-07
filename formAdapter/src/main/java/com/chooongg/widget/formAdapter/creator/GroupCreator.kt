package com.chooongg.widget.formAdapter.creator

import com.chooongg.widget.formAdapter.item.FormItem

class GroupCreator {

    internal val groupList = mutableListOf<FormItem>()

    fun add(item: FormItem) {
        groupList.add(item)
    }
}