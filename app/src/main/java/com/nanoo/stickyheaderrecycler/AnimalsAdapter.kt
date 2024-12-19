package com.nanoo.stickyheaderrecycler

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView


/**
 * Adapter holding a list of animal names of type String. Note that each item must be unique.
 */
abstract class AnimalsAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    private val items = ArrayList<String>()

    init {
        setHasStableIds(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun add(`object`: String) {
        items.add(`object`)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun add(index: Int, `object`: String) {
        items.add(index, `object`)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(collection: Collection<String>?) {
        collection?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addAll(vararg items: String) {
        addAll(items.toList())
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun remove(`object`: String) {
        items.remove(`object`)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): String {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
