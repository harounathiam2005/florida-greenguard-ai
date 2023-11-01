package com.example.invasivespeciesdetection;

import android.os.Bundle;
import android.widget.TextView;
import com.example.invasivespeciesdetection.MainActivity;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PopupHealthActivity extends AppCompatActivity {

    TextView result, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_health_layout);

        result = findViewById(R.id.outputPop);
        description = findViewById(R.id.outputDescPop);

        String label = getIntent().getStringExtra("resultText");

        ChatPortal chatPortal = new ChatPortal(APIKey.getAPIKey() + "", "Give me a very brief description of " + label + " in plants. How can it be fixed?");

        result.setText(getIntent().getStringExtra("resultText"));
        chatPortal.getChatCompletion(new ChatCompletionContinuation(description));

    }
}
