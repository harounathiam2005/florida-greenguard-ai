package com.example.invasivespeciesdetection

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import kotlin.time.Duration.Companion.seconds

class OpenAIAPI(apiKey: String) {

    val openai = OpenAI(
        token = "your-api-key",
        timeout = Timeout(socket = 60.seconds),
        // additional configurations...
    )

    val config = OpenAIConfig(
        token = apiKey,
        timeout = Timeout(socket = 60.seconds),
        // additional configurations...
    )

    val openAI = OpenAI(config)

}