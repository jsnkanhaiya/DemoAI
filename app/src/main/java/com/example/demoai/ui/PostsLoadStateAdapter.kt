package com.example.demoai.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demoai.databinding.UserLoadStateFooterDialogBinding

class PostsLoadStateAdapter :
    LoadStateAdapter<PostsLoadStateAdapter.UserLoadStateViewHolder>() {

    inner class UserLoadStateViewHolder(private val binding: UserLoadStateFooterDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): UserLoadStateViewHolder {
        val binding = UserLoadStateFooterDialogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return UserLoadStateViewHolder(binding)
    }
}
