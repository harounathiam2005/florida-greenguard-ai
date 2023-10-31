package com.example.invasivespeciesdetection;

import android.os.Bundle;
import android.widget.TextView;
import com.example.invasivespeciesdetection.MainActivity;
import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    TextView result, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);

        result = findViewById(R.id.outputPop);
        description = findViewById(R.id.outputDescPop);

        String label = getIntent().getStringExtra("resultText");

        ChatPortal chatPortal = new ChatPortal(APIKey.getAPIKey() + "", label);

        result.setText(getIntent().getStringExtra("resultText"));
        chatPortal.getChatCompletion(new ChatCompletionContinuation(description));
    }
}
