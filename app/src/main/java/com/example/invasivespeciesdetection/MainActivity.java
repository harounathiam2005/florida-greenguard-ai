package com.example.invasivespeciesdetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.invasivespeciesdetection.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

// TODO: Create pop-up to replace result, Add decor to UI, Implement second page w/ alternate model to detect plant health,
//  ! - Implement LLM API to handle descriptions of plants - ! **Implement third page as a detector for general organisms.

public class MainActivity extends AppCompatActivity {

    Button selectButton, captureButton, predictButton;
    static TextView recents;
    ImageView imageView;
    Bitmap bitmap;
    private FragmentManager fragmentManager;
    static ArrayList<String> recentsList = new ArrayList<String>();

    int labelSize = 38;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        FrameLayout popupLayoutContainer = findViewById(R.id.popupLayoutContainer);
        View popupLayout = LayoutInflater.from(this).inflate(R.layout.popup_layout, popupLayoutContainer, false);
        popupLayoutContainer.addView(popupLayout);

 */
        fragmentManager = getFragmentManager();

        getPermission();

        selectButton = findViewById(R.id.selectButton);
        captureButton = findViewById(R.id.captureButton);
        predictButton = findViewById(R.id.identifyButton);

        imageView = findViewById(R.id.imageView);

        //recents = findViewById(R.id.recents);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
            }
        });

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

                    ModelUnquant model = ModelUnquant.newInstance(MainActivity.this);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
                    byteBuffer.order(ByteOrder.nativeOrder());

                    // get 1D array of 224 * 224 pixels in image
                    int[] intValues = new int[224 * 224];
                    bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

                    // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
                    int pixel = 0;
                    for (int i = 0; i < 224; i++) {
                        for (int j = 0; j < 224; j++) {
                            int val = intValues[pixel++]; // RGB
                            byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                            byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                        }
                    }

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    ModelUnquant.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Get AI-Generated description of plant
                    String plantLabel;
                    if (getMax(outputFeature0.getFloatArray()) <= 40) {
                        plantLabel = "Undetermined";
                    } else {
                        plantLabel = getLabels()[getMax(outputFeature0.getFloatArray())] + "";
                    }

                    // Releases model resources if no longer used.C
                    model.close();

                    switchToPopupActivity(plantLabel);
                    //recentsList.add(plantLabel + "");
                    //updateRecents();

                } catch (IOException e) {
                    // TODO Handle the exception
                }

            }

        });

    }

    private void switchToPopupActivity(String label) {
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("resultText", label);
        startActivity(intent);
    }
/*
    // TODO: create list view of previous finds
    public static void updateRecents() {
        if (recentsList.size() == 1) {
            recents.setText(recentsList.get(0));
        } else if (recentsList.size() == 2) {
            recents.setText(recentsList.get(0) + "|" + recentsList.get(1));
        } else if (recentsList.size() >= 3) {
            recents.setText(recentsList.get(recentsList.size() - 1) + "|" + recentsList.get(recentsList.size() - 2) + "|" + recentsList.get(recentsList.size() - 3));
        }
    }

 */

    int getMax(float[] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
                if (arr[i] > arr[max]) {
                    max = i;
                }
            }
        return max;
    }

    String[] getLabels() {

        String[] labels = new String[labelSize];
        int count = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
            String line = bufferedReader.readLine();
            while (line != null && count < labelSize) {
                labels[count] = line;
                count++;
                line = bufferedReader.readLine(); // Advance to the next line
            }
            bufferedReader.close(); // Close the file when done
        } catch (IOException e) {
            e.printStackTrace();
        }

        return labels;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void getPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 11);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 12) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}