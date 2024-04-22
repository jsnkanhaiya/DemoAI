package com.example.demoai.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demoai.R
import com.example.demoai.Utils.Constants
import com.example.demoai.databinding.ActivityPostDetailBinding
import com.example.demoai.datasource.remote.entity.PostsResponseModelItem
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsDetailActivity : AppCompatActivity() {

    private var _binding: ActivityPostDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var gson: Gson

    private val postResponseModelItem: PostsResponseModelItem by lazy {
        gson.fromJson(
            intent.getStringExtra(Constants.KEY_POST_DATA),
            PostsResponseModelItem::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUiBinding()
        setContentView(binding.root)
        initViews()
        setUpOnclickListner()
    }

    private fun setUpOnclickListner() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        binding.tvPostId.text =
            resources.getString(R.string.text_userid, postResponseModelItem.id.toString())
        binding.tvPostTitle.text =
            resources.getString(R.string.text_title, postResponseModelItem.title)
    }

    private fun setUpUiBinding() {
        _binding = ActivityPostDetailBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}