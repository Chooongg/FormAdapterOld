package com.chooongg.widget.formAdapter.item

import android.util.SparseArray

class FormItemFactoryCache {

    private val typeInstances = SparseArray<FormItem>()

    fun register(type: Int, item: FormItem) = if (typeInstances.indexOfKey(type) < 0) {
        typeInstances.put(type, item)
        true
    } else false

    fun get(type: Int): FormItem = typeInstances.get(type)

    fun contains(type: Int): Boolean = typeInstances.indexOfKey(type) >= 0

    fun clear() = typeInstances.clear()

}