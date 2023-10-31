package com.example.invasivespeciesdetection;

import android.os.Build;
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
        StringBuilder builder = new StringBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result.getChoices().forEach(
                    choice -> builder.append(choice.getMessage().getContent())
            );
        }

        textView.setText(builder.toString());
    }

    @Override
    void consumeException(Throwable exception) {
        // Not sure what to do with this. Log it, perhaps?
        // For now, we'll show it on the UI just like the other stuff.
        textView.setText(exception.getLocalizedMessage());
    }
}