package com.example.demoai.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.demoai.Utils.CoreUtility
import com.example.demoai.datasource.ResourceState
import com.example.demoai.datasource.remote.entity.PostsResponseModelItem
import com.example.demoai.interfaces.IAudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val iAudioRepository: IAudioRepository
) : ViewModel() {

    var noInternet: MutableLiveData<Boolean> = MutableLiveData()
    private val _responseData: MutableLiveData<ResourceState<List<PostsResponseModelItem>>> =
        MutableLiveData()
    val responseData: LiveData<ResourceState<List<PostsResponseModelItem>>> =
        _responseData

    private val _postsViewState =
        MutableStateFlow<PagingData<PostsResponseModelItem>>(PagingData.empty())
    val postsViewState: StateFlow<PagingData<PostsResponseModelItem>> = _postsViewState

    fun getPostsList() {
        if (!CoreUtility.isInternetConnected(context)) {
            noInternet.value = true
        } else {
            viewModelScope.launch {
                iAudioRepository.getPostsList().map { pagingData ->
                    pagingData.map {
                        it
                    }
                }.collectLatest {
                    _postsViewState.value = it
                }
            }
        }
    }
}