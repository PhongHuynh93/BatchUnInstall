package com.wind.batchuninstall.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Phong Huynh on 7/11/2020.
 */
object RcvUtil {
    class BaseItemDecoration(private val space: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val pos = parent.getChildAdapterPosition(view)
            if (pos == 0) {
                outRect.top = space
            } else {
                parent.adapter?.let {
                    if (pos == it.itemCount - 1) {
                        outRect.bottom = space
                    }
                }
            }
        }
    }
}