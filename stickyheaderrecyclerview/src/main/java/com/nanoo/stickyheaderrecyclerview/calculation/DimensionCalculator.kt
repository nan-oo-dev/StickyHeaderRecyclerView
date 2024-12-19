package com.nanoo.stickyheaderrecyclerview.calculation

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup.MarginLayoutParams

class DimensionCalculator {
    /**
     * Populates [Rect] with margins for any view.
     *
     *
     * @param margins rect to populate
     * @param view for which to get margins
     */
    fun initMargins(margins: Rect, view: View) {
        val layoutParams = view.layoutParams

        if (layoutParams is MarginLayoutParams) {
            initMarginRect(margins, layoutParams)
        } else {
            margins[0, 0, 0] = 0
        }
    }

    /**
     * Converts [MarginLayoutParams] into a representative [Rect].
     *
     * @param marginRect Rect to be initialized with margins coordinates, where
     * [MarginLayoutParams.leftMargin] is equivalent to [Rect.left], etc.
     * @param marginLayoutParams margins to populate the Rect with
     */
    private fun initMarginRect(marginRect: Rect, marginLayoutParams: MarginLayoutParams) {
        marginRect[marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin] =
            marginLayoutParams.bottomMargin
    }
}
