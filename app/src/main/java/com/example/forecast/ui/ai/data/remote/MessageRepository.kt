package com.example.forecast.ui.ai.data.remote

import com.example.forecast.ui.ai.models.MessageModel
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun fetchMessages(conversationId: String): Flow<List<MessageModel>>
    fun createMessage(message: MessageModel): MessageModel
    fun deleteMessage()
}