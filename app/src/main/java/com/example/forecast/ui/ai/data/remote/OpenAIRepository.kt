package com.example.forecast.ui.ai.data.remote

import com.example.forecast.ui.ai.models.TextCompletionsParam
import kotlinx.coroutines.flow.Flow

interface OpenAIRepository {
    fun textCompletionsWithStream(params: TextCompletionsParam): Flow<String>
}