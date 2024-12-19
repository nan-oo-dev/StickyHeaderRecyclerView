package com.nanoo.stickyheaderrecyclerview.util

import androidx.recyclerview.widget.RecyclerView

interface OrientationProvider {
    fun getOrientation(recyclerView: RecyclerView?): Int

    fun isReverseLayout(recyclerView: RecyclerView?): Boolean
}
