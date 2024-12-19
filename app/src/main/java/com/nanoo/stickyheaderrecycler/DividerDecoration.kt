package com.nanoo.stickyheaderrecycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlin.math.max
import kotlin.math.min

class DividerDecoration(context: Context) : ItemDecoration() {
    private val mDivider: Drawable?

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    private fun getOrientation(parent: RecyclerView): Int {
        val layoutManager: LinearLayoutManager?
        try {
            layoutManager = parent.layoutManager as LinearLayoutManager?
        } catch (e: ClassCastException) {
            throw IllegalStateException(
                "DividerDecoration can only be used with a " +
                        "LinearLayoutManager.", e
            )
        }
        return layoutManager!!.orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        if (getOrientation(parent) == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    fun drawVertical(c: Canvas?, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val recyclerViewTop = parent.paddingTop
        val recyclerViewBottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = max(
                recyclerViewTop.toDouble(),
                (child.bottom + params.bottomMargin).toDouble()
            ).toInt()
            val bottom = min(
                recyclerViewBottom.toDouble(),
                (top + mDivider!!.intrinsicHeight).toDouble()
            ).toInt()
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c!!)
        }
    }

    fun drawHorizontal(c: Canvas?, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val recyclerViewLeft = parent.paddingLeft
        val recyclerViewRight = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left =
                max(recyclerViewLeft.toDouble(), (child.right + params.rightMargin).toDouble())
                    .toInt()
            val right = min(
                recyclerViewRight.toDouble(),
                (left + mDivider!!.intrinsicHeight).toDouble()
            ).toInt()
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c!!)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (getOrientation(parent) == VERTICAL_LIST) {
            outRect[0, 0, 0] = mDivider!!.intrinsicHeight
        } else {
            outRect[0, 0, mDivider!!.intrinsicWidth] = 0
        }
    }

    companion object {
        private val ATTRS = intArrayOf(
            android.R.attr.listDivider
        )

        const val HORIZONTAL_LIST: Int = LinearLayoutManager.HORIZONTAL

        const val VERTICAL_LIST: Int = LinearLayoutManager.VERTICAL
    }
}