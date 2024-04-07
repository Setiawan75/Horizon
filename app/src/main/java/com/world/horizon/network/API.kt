package com.world.horizon.network

import com.world.horizon.model.Articles
import com.world.horizon.model.ListSources
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface API {
    @GET
    suspend fun getListSources(@Url url: String): Response<ListSources>
    @GET
    suspend fun getListArticles(@Url url: String): Response<Articles>
}