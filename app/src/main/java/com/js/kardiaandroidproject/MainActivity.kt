package com.js.kardiaandroidproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.js.kardiaandroidproject.ui.theme.KardiaAndroidProjectTheme
import java.io.Console

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KardiaAndroidProjectTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            GameButton(onClick = {
                Log.d("Tag", "Button clicked");
            }, "Start Game")
        }
    }
}

@Composable
fun GameButton(onClick: () -> Unit, text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width = 200.dp, height = 50.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() })
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = Color.Blue,
                size = Size(size.width, size.height)
            )
        }
        BasicText(
            text = text,
            modifier = Modifier.align(Alignment.Center),
            style = androidx.compose.ui.text.TextStyle(color = Color.White)
        )
    }
}