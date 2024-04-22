package com.example.demoai.datasource.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demoai.Utils.CoreUtility
import com.example.demoai.datasource.remote.entity.PostsResponseModelItem

const val STARTING_PAGE_NUMBER = 1
private const val RECORD_PER_PAGE = 10

class PostPagingDataSource(
    private val postsDataSource: APISolutionsDataSource,
) : PagingSource<Int, PostsResponseModelItem>() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostsResponseModelItem> {

        return try {
            val nextPageNumber = params.key ?: 0
            val response = postsDataSource.getPostsList(nextPageNumber,10)
            LoadResult.Page(
                data = response.body()?: listOf(),
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < 10) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostsResponseModelItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
