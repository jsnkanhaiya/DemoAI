package com.example.demoai.datasource.remote.api

import com.example.demoai.datasource.remote.entity.PostsResponseModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APISolutionsService {
    @GET("/posts?")
    suspend fun getAudioList(
       @Query("_page") pageNumber: Int,
       @Query("_limit") limit: Int,
    ): Response<List<PostsResponseModelItem>>

}