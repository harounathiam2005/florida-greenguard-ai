package com.example.invasivespeciesdetection;

import android.widget.TextView;

import com.aallam.openai.api.chat.ChatCompletion;

public class ChatCompletionContinuation extends CustomContinuation<ChatCompletion> {
    private TextView textView;

    public ChatCompletionContinuation(TextView textView)
    {
        this.textView = textView;
    }

    @Override
    void consumeResult(ChatCompletion result) {
        textView.setText(result.toString());
    }

    @Override
    void consumeException(Throwable exception) {
        // Not sure what to do with this. Log it, perhaps?
        // For now, we'll show it on the UI just like the other stuff.
        textView.setText(exception.getLocalizedMessage());
    }
}