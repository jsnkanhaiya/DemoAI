package com.example.demoai.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoai.R
import com.example.demoai.databinding.ItemAudioBinding
import com.example.demoai.datasource.remote.entity.PostsResponseModelItem

class PostsListAdapter(
    private val context: Context,
    private val onApplyClick: (PostsResponseModelItem) -> Unit
) : PagingDataAdapter<PostsResponseModelItem, PostsListAdapter.PostsListViewHolder>(differCallback) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<PostsResponseModelItem>() {
            override fun areItemsTheSame(
                oldItem: PostsResponseModelItem,
                newItem: PostsResponseModelItem,
            ): Boolean {
                return oldItem.albumId?.equals(newItem.albumId) ?: false
            }

            override fun areContentsTheSame(
                oldItem: PostsResponseModelItem,
                newItem: PostsResponseModelItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class PostsListViewHolder(
        val view: ItemAudioBinding
    ) : RecyclerView.ViewHolder(view.root) {

        @SuppressLint("StringFormatMatches")
        fun bind(userResponseModelItem: PostsResponseModelItem) {
            view.tvAudioName.text =
                context.resources.getString(R.string.text_userid, userResponseModelItem.id)
            view.tvAudioListName.text =
                context.resources.getString(R.string.text_title, userResponseModelItem.title)
            view.root.setOnClickListener {
                onApplyClick(userResponseModelItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsListViewHolder {
        return PostsListViewHolder(
            ItemAudioBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostsListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}