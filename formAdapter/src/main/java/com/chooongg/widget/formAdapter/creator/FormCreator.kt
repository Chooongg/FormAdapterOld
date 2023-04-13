package com.chooongg.widget.formAdapter.creator

import com.chooongg.widget.formAdapter.style.NoneStyle
import com.chooongg.widget.formAdapter.style.Style

class FormCreator {

    internal val parts = mutableListOf<Pair<Style, PartCreator>>()

    fun addPart(style: Style = NoneStyle, block: PartCreator.() -> Unit) {
        parts.add(Pair(style, PartCreator().apply(block)))
    }
}