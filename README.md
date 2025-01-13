# Florida GreenGuard AI
**Streamlining Invasive Species Identification in Florida**

This Android application, developed for the Congressional App Challenge, empowers environmentalists and residents to identify invasive and non-native plant species in Florida.  Leveraging the power of machine learning and a large language model, Florida GreenGuard AI aims to protect Florida's unique ecosystems and biodiversity.
<div align="center">
<img src="https://github.com/harounathiam2005/florida-greenguard-ai/blob/main/public/image-1736789391099.png?raw=true" alt="image-1736789391099.png" />
</div>

[Dossier 0 - Worksheet #1.docx](https://github.com/harounathiam2005/florida-greenguard-ai/blob/main/public/Dossier%200%20-%20Worksheet%20%231.docx)


<div align="center">
<img src="https://github.com/harounathiam2005/florida-greenguard-ai/blob/main/public/1736789511595-LinkedinBannerDuke.jpeg?raw=true" alt="1736789511595-LinkedinBannerDuke.jpeg" />
</div>


## Features
* **Invasive Species Detection:** Uses a TensorFlow Lite model trained on thousands of images to classify plants as native or non-native.
* **Plant Health Assessment:**  Includes a second model to detect potential plant nutrient deficiencies (nitrogen, phosphorus, magnesium, etc.).
* **Descriptive Information:** Integrates the OpenAI API to provide concise descriptions of identified plants, including information on invasiveness.
* **Information Hub:** Features a scrollable page with details on invasive and non-native species, their impact, and model limitations.
* **User-Friendly Interface:**  Intuitive design for easy image selection, classification, and information access.
* **Image Capture:** Allows users to take pictures directly through the app.

## Installation
1. **Prerequisites:**  Android device with Android Studio.  An OpenAI API key is required for the descriptive functionality (see Configuration).
2. **Clone the Repository:** Clone this repository to your local machine using `git clone <repository_url>`.
3. **Open in Android Studio:** Open the project in Android Studio.
4. **Build and Install:** Build and install the application on your Android device using Android Studio's build and run functionality.

## Technologies Used (Tech Stack)
* **Machine Learning:**
    * Google Teachable Machine
    * TensorFlow Lite
    * TensorFlow Lite GPU (for improved performance)
* **Large Language Model:**
    * OpenAI ChatCompletion API; GPT-3.5-Turbo LLM
    * `com.aallam.openai:openai-client` Kotlin library
* **Mobile Development:**
    * Android Studio
    * Kotlin
    * Java
    * XML

## Configuration
Before running the app, you must obtain an OpenAI API key.  

1. Sign up for an OpenAI account.
2. Create a new API key.
3. Replace the placeholder API key in the `APIKey.java` file with your newly generated key.

## Dependencies
The project uses the following dependencies:

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'org.tensorflow:tensorflow-lite-support:0.1.0'
    implementation 'org.tensorflow:tensorflow-lite-metadata:0.1.0'
    implementation 'org.tensorflow:tensorflow-lite-gpu:2.3.0'
    implementation 'androidx.core:core-ktx:1.12.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    implementation platform('com.aallam.openai:openai-client-bom:3.5.0')
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.2"
    implementation 'com.aallam.openai:openai-client'
    runtimeOnly 'io.ktor:ktor-client-okhttp'
}
```

## Contributing
Contributions are welcome! Please open an issue or submit a pull request.

## Testing
Unit testing and integration testing were not implemented in this version.

## License
[Specify your license here, e.g., MIT License]

## Usage
To use the Florida GreenGuard AI application, first install it on your Android device (see Installation).  After obtaining and configuring an OpenAI API key (see Configuration), launch the app. You can then select or capture an image of a plant. The app will classify the plant as native or non-native using a TensorFlow Lite model and provide a brief description via the OpenAI API. A second model assesses potential plant nutrient deficiencies.  Additional information on invasive species is available within the app's information hub.

*Made with [Etchr](https://etchr.dev)*