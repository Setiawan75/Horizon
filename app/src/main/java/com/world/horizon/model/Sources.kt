package com.world.horizon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sources(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var url: String = "",
    var category: String = "",
    var languange: String = "",
    var country: String = "",
) : Parcelable

data class ListSources(val sources: ArrayList<Sources>)
