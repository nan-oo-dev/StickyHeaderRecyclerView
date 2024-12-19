package com.nanoo.stickyheaderrecyclerview.caching

import android.util.LongSparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nanoo.stickyheaderrecyclerview.StickyRecyclerHeadersAdapter
import com.nanoo.stickyheaderrecyclerview.util.OrientationProvider

/**
 * An implementation of [HeaderProvider] that creates and caches header views
 */
class HeaderViewCache(
    private val mAdapter: StickyRecyclerHeadersAdapter<*>,
    private val mOrientationProvider: OrientationProvider
) : HeaderProvider {
    private val mHeaderViews = LongSparseArray<View>()

    override fun getHeader(recyclerView: RecyclerView?, position: Int): View {
        val headerId = mAdapter.getHeaderId(position)

        var header = mHeaderViews[headerId]
        if (header == null) {
            //TODO - recycle views
            val viewHolder = mAdapter.onCreateHeaderViewHolder(recyclerView!!)
            mAdapter.onBindHeaderViewHolder(viewHolder, position)
            header = viewHolder.itemView
            if (header.layoutParams == null) {
                header.setLayoutParams(
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
            }

            val widthSpec: Int
            val heightSpec: Int

            if (mOrientationProvider.getOrientation(recyclerView) == LinearLayoutManager.VERTICAL) {
                widthSpec = View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY)
                heightSpec =
                    View.MeasureSpec.makeMeasureSpec(recyclerView.height, View.MeasureSpec.UNSPECIFIED)
            } else {
                widthSpec =
                    View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.UNSPECIFIED)
                heightSpec =
                    View.MeasureSpec.makeMeasureSpec(recyclerView.height, View.MeasureSpec.EXACTLY)
            }

            val childWidth = ViewGroup.getChildMeasureSpec(
                widthSpec,
                recyclerView.paddingLeft + recyclerView.paddingRight, header.layoutParams.width
            )
            val childHeight = ViewGroup.getChildMeasureSpec(
                heightSpec,
                recyclerView.paddingTop + recyclerView.paddingBottom, header.layoutParams.height
            )
            header.measure(childWidth, childHeight)
            header.layout(0, 0, header.measuredWidth, header.measuredHeight)
            mHeaderViews.put(headerId, header)
        }
        return header
    }

    override fun invalidate() {
        mHeaderViews.clear()
    }
}
