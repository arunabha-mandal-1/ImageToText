package com.arunabha.imagetotextapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.arunabha.imagetotextapp.screens.SelectAndDisplayImageScreen
import com.arunabha.imagetotextapp.ui.theme.ImageToTextAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            ImageToTextAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SelectAndDisplayImageScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

