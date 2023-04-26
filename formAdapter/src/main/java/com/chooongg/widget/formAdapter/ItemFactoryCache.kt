package com.chooongg.widget.formAdapter

import android.util.SparseArray
import com.chooongg.widget.formAdapter.item.BaseForm

internal class ItemFactoryCache {

    private val typeInstances = SparseArray<BaseForm>()

    fun register(type: Int, item: BaseForm) = if (typeInstances.indexOfKey(type) < 0) {
        typeInstances.put(type, item)
        true
    } else false

    fun get(type: Int): BaseForm = typeInstances.get(type)

    fun contains(type: Int): Boolean = typeInstances.indexOfKey(type) >= 0

    fun clear() = typeInstances.clear()

}