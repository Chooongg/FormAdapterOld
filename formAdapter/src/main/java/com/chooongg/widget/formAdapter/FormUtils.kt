package com.chooongg.widget.formAdapter

import android.content.Context
import android.content.res.Resources
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.res.use

object FormUtils {

    fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    fun attrResourcesId(context: Context, @AttrRes id: Int, defValue: Int) =
        context.obtainStyledAttributes(intArrayOf(id)).use {
            it.getResourceId(0, defValue)
        }
}