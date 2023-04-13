package com.chooongg.widget.formAdapter

import androidx.annotation.IntDef
import com.chooongg.widget.formAdapter.enum.FormBoundary

/**
 * 边界信息
 */
data class Boundary(
    @BoundaryType var start: Int,
    @BoundaryType var top: Int,
    @BoundaryType var end: Int,
    @BoundaryType var bottom: Int
) {
    constructor() : this(NONE)
    constructor(any: Int) : this(any, any, any, any)

    companion object {
        const val NONE = 0
        const val LOCAL = 1
        const val GLOBAL = 2
    }

    @IntDef(NONE, LOCAL, GLOBAL)
    annotation class BoundaryType
}