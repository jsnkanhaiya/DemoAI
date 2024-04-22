package com.example.demoai.datasource.remote

import com.example.demoai.datasource.remote.entity.PostsResponseModelItem
import retrofit2.Response

interface APISolutionsDataSource {
    suspend fun getPostsList(page : Int, limit : Int): Response<List<PostsResponseModelItem>>
}