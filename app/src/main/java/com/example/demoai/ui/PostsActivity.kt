package com.example.demoai.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoai.Utils.Constants.KEY_POST_DATA
import com.example.demoai.databinding.ActivityMainBinding
import com.example.demoai.extension.gone
import com.example.demoai.extension.visible
import com.example.demoai.ui.viewmodel.PostsViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {

    private lateinit var layoutManagers: LinearLayoutManager
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostsViewModel by viewModels()

    @Inject
    lateinit var gson: Gson

    private val postsListAdapter by lazy {
        PostsListAdapter(this) { postItem ->
            val intent = Intent(this, PostsDetailActivity::class.java).apply {
                putExtra(KEY_POST_DATA, gson.toJson(postItem))
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUiBinding()
        setContentView(binding.root)
        setRecyclerView()
        setUpObserver()
        //binding.progressBar.visible()
        viewModel.getPostsList()
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            viewModel.postsViewState.collectLatest {
                postsListAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setUpRecyclerViewListener() {
        postsListAdapter.addLoadStateListener {
            if (it.source.refresh is LoadState.NotLoading) {
                binding.progressBar.gone()
                //   showEmptyErrorView()
            } else if (it.source.refresh is LoadState.Loading) {
                binding.progressBar.visible()
                //   showEmptyErrorView()
            }
        }

        binding.rvPosts.adapter = postsListAdapter.withLoadStateFooter(
            footer = PostsLoadStateAdapter(),
        )
    }

    private fun setRecyclerView() {
        layoutManagers = LinearLayoutManager(this)
        binding.rvPosts.apply {
            layoutManager = layoutManagers
            adapter = postsListAdapter
        }
        binding.rvPosts.isNestedScrollingEnabled = false;

        setUpRecyclerViewListener()
    }

    private fun setUpUiBinding() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}