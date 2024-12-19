package com.nanoo.stickyheaderrecyclerview

import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

class StickyRecyclerHeadersTouchListener(
    recyclerView: RecyclerView,
    decor: StickyRecyclerHeadersDecoration
) : OnItemTouchListener {
    private val mTapDetector: GestureDetector
    private val mRecyclerView: RecyclerView
    private val mDecor: StickyRecyclerHeadersDecoration
    private var mOnHeaderClickListener: OnHeaderClickListener? = null

    interface OnHeaderClickListener {
        fun onHeaderClick(header: View?, position: Int, headerId: Long)
    }

    init {
        mTapDetector = GestureDetector(recyclerView.context, SingleTapDetector())
        mRecyclerView = recyclerView
        mDecor = decor
    }

    val adapter: StickyRecyclerHeadersAdapter<*>?
        get() {
            if (mRecyclerView.adapter is StickyRecyclerHeadersAdapter<*>) {
                return mRecyclerView.adapter as StickyRecyclerHeadersAdapter<*>?
            } else {
                throw IllegalStateException(
                    "A RecyclerView with " +
                            StickyRecyclerHeadersTouchListener::class.java.simpleName +
                            " requires a " + StickyRecyclerHeadersAdapter::class.java.simpleName
                )
            }
        }


    fun setOnHeaderClickListener(listener: OnHeaderClickListener?) {
        mOnHeaderClickListener = listener
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        if (this.mOnHeaderClickListener != null) {
            val tapDetectorResponse = mTapDetector.onTouchEvent(e)
            if (tapDetectorResponse) {
                // Don't return false if a single tap is detected
                return true
            }
            if (e.action == MotionEvent.ACTION_DOWN) {
                val position = mDecor.findHeaderPositionUnder(e.x.toInt(), e.y.toInt())
                return position != -1
            }
        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, e: MotionEvent) { /* do nothing? */
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // do nothing
    }

    private inner class SingleTapDetector : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val position = mDecor.findHeaderPositionUnder(e.x.toInt(), e.y.toInt())
            if (position != -1) {
                val headerView = mDecor.getHeaderView(mRecyclerView, position)
                adapter?.let {
                    val headerId: Long = adapter!!.getHeaderId(position)
                    mOnHeaderClickListener!!.onHeaderClick(headerView, position, headerId)
                }
                mRecyclerView.playSoundEffect(SoundEffectConstants.CLICK)
                headerView?.onTouchEvent(e)
                return true
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }
    }
}
