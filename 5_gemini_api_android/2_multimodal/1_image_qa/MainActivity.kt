package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.helloworld.ui.theme.HelloWorldTheme
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloWorldTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        lifecycleScope.launch {
            RunGemini()
        }
    }

    suspend fun RunGemini() {
        try {
            // 모델 준비
            val model = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = BuildConfig.apiKey
            )

            // 이미지 준비
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)

            // 추론 실행
            val response = model.generateContent(content {
                image(bitmap)
                text("이것은 무슨 이미지 입니까?")
            })
            android.util.Log.d("gemini", response.text.toString())
        } catch (e: Exception) {
            android.util.Log.e("gemini", "Error: ${e.message}", e)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloWorldTheme {
        Greeting("Android")
    }
}