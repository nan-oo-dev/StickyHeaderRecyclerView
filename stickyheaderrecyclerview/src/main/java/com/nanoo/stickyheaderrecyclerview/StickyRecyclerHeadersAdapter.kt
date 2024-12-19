package com.nanoo.stickyheaderrecyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface StickyRecyclerHeadersAdapter<VH : RecyclerView.ViewHolder> {

    fun getHeaderId(position: Int): Long

    fun onCreateHeaderViewHolder(parent: ViewGroup): VH

    fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    fun getItemCount(): Int
}
