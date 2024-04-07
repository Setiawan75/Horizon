package com.world.horizon.model

data class Sources(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var url: String = "",
    var category: String = "",
    var languange: String = "",
    var country: String = "",
)

data class ListSources(val sources: ArrayList<Sources>)
