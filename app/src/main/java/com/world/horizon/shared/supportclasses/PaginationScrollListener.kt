package com.world.horizon.shared.supportclasses

import androidx.core.widget.NestedScrollView

abstract class PaginationScrollListener: NestedScrollView.OnScrollChangeListener  {
    private var isBottomReached = false

    /**
     * remaining height before load more
     */
    var visibleThreshold = 100

    /**
     * remaining height before load more in percent of view height
     */
    var visibleThresholdPercentage = 0.0
        set(value) {
            field = if (value > 1.0)
                1.0
            else if (value < 0.0) {
                0.0
            } else {
                value
            }
        }

    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        val view = v.getChildAt(v.childCount - 1)
        val diff = view.bottom - (v.height + scrollY)
        val isScrollDown = scrollY > oldScrollY
        val threshold = if (visibleThresholdPercentage > 0.0) {
            (v.height * visibleThresholdPercentage).toInt()
        } else {
            visibleThreshold
        }

        if (diff <= threshold && !isBottomReached && isScrollDown) {
            isBottomReached = true
            onReachBottom()
        }

        if (isBottomReached && diff > threshold) {
            isBottomReached = false
        }
    }

    abstract fun onReachBottom()
}