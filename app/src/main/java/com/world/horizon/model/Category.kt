package com.world.horizon.model

import android.os.Parcelable
import com.world.horizon.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var title: String = "",
    var category: String = "",
    var color: Int = R.color.black
) : Parcelable
