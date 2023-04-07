package com.chooongg.widget.formAdapter.style

import android.view.View
import android.view.ViewGroup
import com.chooongg.widget.formAdapter.item.FormItem
import com.chooongg.widget.formAdapter.typeset.NormalHorizontalTypeset
import com.chooongg.widget.formAdapter.typeset.Typeset

class NoneStyle(typeset: Typeset = NormalHorizontalTypeset) : Style(typeset) {
    override fun onCreateItemParent(parent: ViewGroup): ViewGroup = parent
    override fun onBindItemParentLayout(parent: View, item: FormItem): ViewGroup = parent
}