package com.example.forecast.ui.ai.data.api

import com.example.forecast.ui.ai.constants.textCompletionsEndpoint
import com.example.forecast.ui.ai.constants.textCompletionsTurboEndpoint
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface OpenAIApi {
    @POST(textCompletionsEndpoint)
    @Streaming
    fun textCompletionsWithStream(@Body body: JsonObject): Call<ResponseBody>

    @POST(textCompletionsTurboEndpoint)
    @Streaming
    fun textCompletionsTurboWithStream(@Body body: JsonObject): Call<ResponseBody>
}