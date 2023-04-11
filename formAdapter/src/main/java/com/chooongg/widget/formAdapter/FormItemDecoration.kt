package com.chooongg.widget.formAdapter

import android.content.res.Resources
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager

class FormItemDecoration(private val formAdapter: BaseFormAdapter) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val isRtl = parent.layoutDirection == View.LAYOUT_DIRECTION_RTL
        val holder = parent.getChildViewHolder(view)
        val globalAdapter = formAdapter.adapter
        val adapter =
            globalAdapter.getWrappedAdapterAndPosition(holder.absoluteAdapterPosition).first
        outRect.top = if (holder.bindingAdapterPosition == 0) {
            dp2px(16f)
        } else 0
        outRect.bottom = if (holder.absoluteAdapterPosition == globalAdapter.itemCount) {
            dp2px(16f)
        } else if (holder.bindingAdapterPosition == adapter.itemCount) {
            dp2px(8f)
        } else 0
        if (parent.layoutManager is FlexboxLayoutManager) {
            val layoutManager = parent.layoutManager as FlexboxLayoutManager
            Log.e("ItemDecoration", "----------")
            Log.e("ItemDecoration", holder.absoluteAdapterPosition.toString())
            var index = 0
            line@ for (flexLine in layoutManager.flexLines) {
                Log.e("ItemDecoration", "flexLine: $flexLine")
                if (holder.absoluteAdapterPosition >= index && holder.absoluteAdapterPosition < index + flexLine.itemCountNotGone) {
                    if (flexLine.itemCountNotGone == 1) {
                        Log.e("ItemDecoration", "singleLine")
                        outRect.left = dp2px(16f)
                        outRect.right = dp2px(16f)
                    } else {
                        if (holder.absoluteAdapterPosition == index) {
                            Log.e("ItemDecoration", "First")
                            if (isRtl) {
                                outRect.left = 0
                                outRect.right = dp2px(16f)
                            } else {
                                outRect.left = dp2px(16f)
                                outRect.right = 0
                            }
                        } else if (holder.absoluteAdapterPosition == index + flexLine.itemCount - 1) {
                            Log.e("ItemDecoration", "Last")
                            if (isRtl) {
                                outRect.left = dp2px(16f)
                                outRect.right = 0
                            } else {
                                outRect.left = 0
                                outRect.right = dp2px(16f)
                            }
                        } else {
                            Log.e("ItemDecoration", "None")
                            outRect.left = 0
                            outRect.right = 0
                        }
                    }
                    break@line
                }
                index += flexLine.itemCount
            }
        }
        Log.e("ItemDecoration", outRect.toString())
    }

    private fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}