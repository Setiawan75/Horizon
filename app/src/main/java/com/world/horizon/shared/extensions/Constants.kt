package com.world.horizon.shared.extensions

object Constants {
    interface API {
        companion object{
            const val API_KEY = "&apiKey=6f6941dff974452abd623aa7b72f86ad"
            const val BASE_URL = "https://newsapi.org/v2/"
            const val CATEGORY = "top-headlines/sources?category="
            const val DOMAIN = "everything?domains="
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