package com.example.invasivespeciesdetection

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlin.time.Duration.Companion.seconds

class ChatPortal(apiKey: String, prompt: String) {

        val openai = OpenAI(
            token = apiKey,
            timeout = Timeout(socket = 60.seconds),
            // additional configurations...
        )

         val chatCompletionRequest = ChatCompletionRequest(
             model = ModelId("gpt-3.5-turbo"),
             messages = listOf(
                 ChatMessage(
                     role = ChatRole.System,
                     content = "Give me a very brief description $prompt."
                 ),
                 ChatMessage(
                     role = ChatRole.User,
                     content = "Hello!"
                 )
             )
         )

     suspend fun getChatCompletion(): ChatCompletion {
        val completion = openai.chatCompletion(chatCompletionRequest)
        return completion
    }

}