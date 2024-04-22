package com.example.demoai.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.demoai.datasource.NetworkBoundSource
import com.example.demoai.datasource.ResourceState
import com.example.demoai.datasource.remote.APISolutionsDataSource
import com.example.demoai.datasource.remote.PostPagingDataSource
import com.example.demoai.datasource.remote.STARTING_PAGE_NUMBER
import com.example.demoai.datasource.remote.entity.PostsResponseModel
import com.example.demoai.datasource.remote.entity.PostsResponseModelItem
import com.example.demoai.interfaces.IAudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val apiSolutionsDataSource: APISolutionsDataSource,
) : IAudioRepository {

    override fun getPostsList(): Flow<PagingData<PostsResponseModelItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = STARTING_PAGE_NUMBER,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                Log.d("EventDataSource", "EventDataSource called")
                PostPagingDataSource(
                    apiSolutionsDataSource
                )
            },
            initialKey = STARTING_PAGE_NUMBER,
        ).flow
    }

}