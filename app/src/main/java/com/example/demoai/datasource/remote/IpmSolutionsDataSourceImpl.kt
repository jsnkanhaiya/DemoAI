package com.example.demoai.datasource.remote

import com.example.demoai.datasource.remote.api.APISolutionsService
import com.example.demoai.datasource.remote.entity.PostsResponseModelItem
import retrofit2.Response
import javax.inject.Inject

class IpmSolutionsDataSourceImpl @Inject constructor(
    private val apiSolutionsService: APISolutionsService
) : APISolutionsDataSource {

    override suspend fun getPostsList(page : Int, limit : Int): Response<List<PostsResponseModelItem>> {
        return apiSolutionsService.getAudioList(page,limit)
    }

}