package com.example.myassistant

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myassistant.ui.theme.MyAssistantTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
  lateinit  var startForResult : ActivityResultLauncher<Intent>
    var speakText by
    mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode== RESULT_OK && it.data!=null){
                var resultData = it.data
                val resultArray = resultData?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
               speakText =resultArray?.get(0).toString()
            }
        }
        setContent {
            MyAssistantTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SpeechToTextApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                SpeechToTextApp()
            }
        }
    }

    @Composable
    fun SpeechToTextApp(modifier: Modifier = Modifier) {
//        var speakText = remember {
//            mutableStateOf("")
//        }
        var context = LocalContext.current
        Box (modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now")
                    startForResult.launch(intent)
                }) {
                    Icon(Icons.Rounded.Mic, contentDescription = null)
                }
                Text(text = speakText)
            }
        }
    }
}
