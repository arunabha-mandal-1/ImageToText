package com.arunabha.imagetotextapp.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun SelectAndDisplayImageScreen(modifier: Modifier) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    var image by remember { mutableStateOf<InputImage?>(null) }
    var result by remember { mutableStateOf<Text?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            image = InputImage.fromFilePath(context, uri)
        }
    }



    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // image
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    contentScale = ContentScale.Fit
                )
            }

            // select image button
            Button(
                modifier = Modifier.padding(bottom = 5.dp),
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text("Select Image")
            }

            // show text button
            Button(
                modifier = Modifier.padding(bottom = 5.dp),
                onClick = {
                    recognizer.process(image!!)
                        .addOnSuccessListener { text ->
                            result = text
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to recognize text!", Toast.LENGTH_SHORT)
                                .show()
                        }
                },
                enabled = image != null
            ) {
                Text("Show Text")
            }

            // image text
            LazyColumn {
                item {
                    Text(
                        text = result?.text ?: "No Text!"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAndDisplayImageScreenPreview() {
    SelectAndDisplayImageScreen(Modifier)
}