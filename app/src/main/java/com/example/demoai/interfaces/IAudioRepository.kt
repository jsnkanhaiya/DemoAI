package com.example.demoai.interfaces

import androidx.paging.PagingData
import com.example.demoai.datasource.ResourceState
import com.example.demoai.datasource.remote.entity.PostsResponseModelItem
import kotlinx.coroutines.flow.Flow

interface IAudioRepository {
    fun getPostsList(): Flow<PagingData<PostsResponseModelItem>>
}