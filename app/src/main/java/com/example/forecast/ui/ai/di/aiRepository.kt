package com.example.forecast.ui.ai.di

import com.example.forecast.ui.ai.data.remote.ConversationRepository
import com.example.forecast.ui.ai.data.remote.ConversationRepositoryImpl
import com.example.forecast.ui.ai.data.remote.MessageRepository
import com.example.forecast.ui.ai.data.remote.MessageRepositoryImpl
import com.example.forecast.ui.ai.data.remote.OpenAIRepository
import com.example.forecast.ui.ai.data.remote.OpenAIRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun conversationRepository(
        repo: ConversationRepositoryImpl
    ): ConversationRepository

    @Binds
    abstract fun messageRepository(
        repo: MessageRepositoryImpl
    ): MessageRepository

    @Binds
    abstract fun openAIRepository(
        repo: OpenAIRepositoryImpl
    ): OpenAIRepository
}
