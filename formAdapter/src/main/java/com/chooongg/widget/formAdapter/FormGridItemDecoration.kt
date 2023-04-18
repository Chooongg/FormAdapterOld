package com.chooongg.widget.formAdapter

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class FormGridItemDecoration(context: Context, private val formAdapter: BaseFormAdapter) :
    ItemDecoration() {

    private val verticalMarginGlobal =
        context.resources.getDimensionPixelOffset(R.dimen.formVerticalMarginGlobal)
    private val verticalMarginLocal =
        context.resources.getDimensionPixelOffset(R.dimen.formVerticalMarginLocal)
    private val horizontalMarginGlobal =
        context.resources.getDimensionPixelOffset(R.dimen.formHorizontalMarginGlobal)
    private val horizontalMarginLocal =
        context.resources.getDimensionPixelOffset(R.dimen.formHorizontalMarginLocal)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val holder = parent.getChildViewHolder(view)
        val adapter =
            formAdapter.adapter.getWrappedAdapterAndPosition(holder.absoluteAdapterPosition).first
        if (adapter !is FormPartAdapter || !adapter.style.isNeedDecorationMargins) return
        val item = adapter.getItem(holder.bindingAdapterPosition)
        Log.e("ItemDecoration", "${holder.absoluteAdapterPosition}\n${item.boundary}")
        outRect.top = if (holder.absoluteAdapterPosition == 0) {
            verticalMarginGlobal
        } else if (holder.bindingAdapterPosition == 0 || item.itemPosition == 0) {
            verticalMarginLocal
        } else if (item.singleLineCount > 1) {
            verticalMarginLocal
        } else 0
        outRect.top = when (item.boundary.top) {
            Boundary.GLOBAL -> verticalMarginGlobal
            Boundary.LOCAL -> verticalMarginLocal
            else -> 0
        }

        outRect.bottom = when (item.boundary.bottom) {
            Boundary.GLOBAL -> verticalMarginGlobal
            Boundary.LOCAL -> verticalMarginLocal
            else -> 0
        }
        if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            if (adapter.style.isNeedDecorationMargins) {
                when (item.singleLineCount) {
                    1, 2 -> {
                        outRect.left = when (item.boundary.start) {
                            Boundary.GLOBAL -> horizontalMarginGlobal
                            Boundary.LOCAL -> horizontalMarginLocal
                            else -> 0
                        }
                        outRect.right = when (item.boundary.end) {
                            Boundary.GLOBAL -> horizontalMarginGlobal
                            Boundary.LOCAL -> horizontalMarginLocal
                            else -> 0
                        }
                    }

                    3 -> {
                        when (item.singleLineIndex) {
                            0 -> {
                                outRect.left = horizontalMarginGlobal
                                outRect.right = -horizontalMarginGlobal / 3
                            }

                            1 -> {
                                outRect.left = horizontalMarginGlobal / 3
                                outRect.right = horizontalMarginGlobal / 3
                            }

                            2 -> {
                                outRect.left = -horizontalMarginGlobal / 3
                                outRect.right = horizontalMarginGlobal
                            }
                        }
                    }

                    4 -> {
                        when (item.singleLineIndex) {
                            0 -> {
                                outRect.left = horizontalMarginGlobal
                                outRect.right = -horizontalMarginGlobal / 2
                            }

                            1 -> {
                                outRect.left = horizontalMarginGlobal / 2
                                outRect.right = 0
                            }

                            2 -> {
                                outRect.left = 0
                                outRect.right = horizontalMarginGlobal / 2
                            }

                            3 -> {
                                outRect.left = -horizontalMarginGlobal / 2
                                outRect.right = horizontalMarginGlobal
                            }
                        }
                    }

                    5 -> {
                        when (item.singleLineIndex) {
                            0 -> {
                                outRect.left = horizontalMarginGlobal
                                outRect.right = -(horizontalMarginGlobal / 5f * 3).toInt()
                            }

                            1 -> {
                                outRect.left = (horizontalMarginGlobal / 5f * 3).toInt()
                                outRect.right = -horizontalMarginGlobal / 5
                            }

                            2 -> {
                                outRect.left = horizontalMarginGlobal / 5
                                outRect.right = horizontalMarginGlobal / 5
                            }

                            3 -> {
                                outRect.left = -horizontalMarginGlobal / 5
                                outRect.right = (horizontalMarginGlobal / 5f * 3).toInt()
                            }

                            4 -> {
                                outRect.left = -(horizontalMarginGlobal / 5f * 3).toInt()
                                outRect.right = horizontalMarginGlobal
                            }
                        }
                    }
                }
            } else {
                outRect.left = when (item.boundary.start) {
                    Boundary.GLOBAL -> horizontalMarginGlobal
                    Boundary.LOCAL -> horizontalMarginLocal
                    else -> 0
                }
                outRect.right = when (item.boundary.end) {
                    Boundary.GLOBAL -> horizontalMarginGlobal
                    Boundary.LOCAL -> horizontalMarginLocal
                    else -> 0
                }
            }
        } else {
            if (adapter.style.isNeedDecorationMargins) {
                when (item.singleLineCount) {
                    1, 2 -> {
                        outRect.left = when (item.boundary.end) {
                            Boundary.GLOBAL -> horizontalMarginGlobal
                            Boundary.LOCAL -> horizontalMarginLocal
                            else -> 0
                        }
                        outRect.right = when (item.boundary.start
                        ) {
                            Boundary.GLOBAL -> horizontalMarginGlobal
                            Boundary.LOCAL -> horizontalMarginLocal
                            else -> 0
                        }
                    }

                    3 -> {
                        when (item.singleLineIndex) {
                            0 -> {
                                outRect.left = -horizontalMarginGlobal / 3
                                outRect.right = horizontalMarginGlobal
                            }

                            1 -> {
                                outRect.left = horizontalMarginGlobal / 3
                                outRect.right = horizontalMarginGlobal / 3
                            }

                            2 -> {
                                outRect.left = horizontalMarginGlobal
                                outRect.right = -horizontalMarginGlobal / 3
                            }
                        }
                    }

                    4 -> {
                        when (item.singleLineIndex) {
                            0 -> {
                                outRect.left = -horizontalMarginGlobal / 2
                                outRect.right = horizontalMarginGlobal
                            }

                            1 -> {
                                outRect.left = 0
                                outRect.right = horizontalMarginGlobal / 2
                            }

                            2 -> {
                                outRect.left = horizontalMarginGlobal / 2
                                outRect.right = 0
                            }

                            3 -> {
                                outRect.left = horizontalMarginGlobal
                                outRect.right = -horizontalMarginGlobal / 2
                            }
                        }
                    }

                    5 -> {
                        when (item.singleLineIndex) {
                            0 -> {
                                outRect.left = -(horizontalMarginGlobal / 5f * 3).toInt()
                                outRect.right = horizontalMarginGlobal
                            }

                            1 -> {
                                outRect.left = -horizontalMarginGlobal / 5
                                outRect.right = (horizontalMarginGlobal / 5f * 3).toInt()
                            }

                            2 -> {
                                outRect.left = horizontalMarginGlobal / 5
                                outRect.right = horizontalMarginGlobal / 5
                            }

                            3 -> {
                                outRect.left = (horizontalMarginGlobal / 5f * 3).toInt()
                                outRect.right = -horizontalMarginGlobal / 5
                            }

                            4 -> {
                                outRect.left = horizontalMarginGlobal
                                outRect.right = -(horizontalMarginGlobal / 5f * 3).toInt()
                            }
                        }
                    }
                }
            } else {
                outRect.left = when (item.boundary.end) {
                    Boundary.GLOBAL -> horizontalMarginGlobal
                    Boundary.LOCAL -> horizontalMarginLocal
                    else -> 0
                }
                outRect.right = when (item.boundary.start) {
                    Boundary.GLOBAL -> horizontalMarginGlobal
                    Boundary.LOCAL -> horizontalMarginLocal
                    else -> 0
                }
            }
        }
    }
}