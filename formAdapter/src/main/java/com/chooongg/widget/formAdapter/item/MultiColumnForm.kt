package com.chooongg.widget.formAdapter.item

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import com.chooongg.widget.formAdapter.FormPartAdapter
import com.chooongg.widget.formAdapter.FormViewHolder

class MultiColumnForm internal constructor() : BaseForm(null, null) {

    @IntRange(from = 1, to = 5)
    var maxColumn = 2

    internal var items = ArrayList<BaseForm>()

    override fun onCreateContentView(adapter: FormPartAdapter, parent: ViewGroup): View =
        View(parent.context)

    override fun onBindContentView(adapter: FormPartAdapter, holder: FormViewHolder) = Unit
}