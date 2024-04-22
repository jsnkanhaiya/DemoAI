package com.example.demoai.Utils

import android.content.Context
import android.content.Intent
import com.example.demoai.ui.PostsActivity

class ActivityProviderImpl(private val context: Context) : ActivityProvider {
  override fun mainActivityIntent(): Intent = Intent(context, PostsActivity::class.java)
}