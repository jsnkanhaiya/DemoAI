package com.example.demoai.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class PostsResponseModel(

    @field:SerializedName("ImageUserResponseModel")
    val audioResponseModel: List<PostsResponseModelItem>? = null
)

data class PostsResponseModelItem(

    @field:SerializedName("albumId")
    val albumId: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)