package com.world.horizon.shared.extensions

object Constants {
    interface API {
        companion object{
            const val API_KEY = "&apiKey=f9d4c022b77f420f82ec1719ce60c13e"
            const val BASE_URL = "https://newsapi.org/v2/"
            const val CATEGORY = "top-headlines/sources?category="
            const val DOMAIN = "everything?domains="
            const val PAGE = "&page="
            const val SEARCH = "&q="
        }
    }

    interface PARAM {
        companion object{
            const val BUSINESS = "business"
            const val ENTERTAINMENT = "entertainment"
            const val GENERAL = "general"
            const val HEALTH = "health"
            const val SCIENCE = "science"
            const val SPORTS = "sports"
            const val TECHNOLOGY = "technology"
        }
    }
}