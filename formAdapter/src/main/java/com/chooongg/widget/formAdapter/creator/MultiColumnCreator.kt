package com.chooongg.widget.formAdapter.creator

import androidx.annotation.IntRange

class MultiColumnCreator : GroupCreator() {

    /**
     * 是否在组边缘展示
     */
    var isShowOnEdge = true

    /**
     * 最大列数
     */
    @IntRange(from = 1, to = 5)
    var maxColumn = 2
}