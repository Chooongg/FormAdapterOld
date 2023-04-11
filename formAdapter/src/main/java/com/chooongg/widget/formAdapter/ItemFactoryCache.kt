package com.chooongg.widget.formAdapter

import android.util.SparseArray
import com.chooongg.widget.formAdapter.item.FormItem

internal class ItemFactoryCache {

    private val typeInstances = SparseArray<FormItem>()

    fun register(type: Int, item: FormItem) = if (typeInstances.indexOfKey(type) < 0) {
        typeInstances.put(type, item)
        true
    } else false

    fun get(type: Int): FormItem = typeInstances.get(type)

    fun contains(type: Int): Boolean = typeInstances.indexOfKey(type) >= 0

    fun clear() = typeInstances.clear()

}