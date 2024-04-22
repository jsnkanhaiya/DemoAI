package com.example.demoai.di

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.demoai.Utils.ActivityProvider
import com.example.demoai.Utils.ActivityProviderImpl
import com.example.demoai.datasource.remote.APISolutionsDataSource
import com.example.demoai.interfaces.IAudioRepository
import com.example.demoai.repository.PostsRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class AppModule {
    @Singleton
    @Provides
    fun providesActivityProvider(@ApplicationContext context: Context): ActivityProvider =
        ActivityProviderImpl(context)

    @Singleton
    @Provides
    fun providesAudioRepository(
        apiSolutionsDataSource: APISolutionsDataSource
    ): IAudioRepository = PostsRepository(apiSolutionsDataSource)


    @Provides
    fun provideMediaPlayer(): MediaPlayer{
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        return mediaPlayer
    }

    @Singleton
    @Provides
    fun provideGson(): Gson{
        return Gson()
    }
}