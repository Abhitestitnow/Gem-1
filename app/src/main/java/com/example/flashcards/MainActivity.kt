// app/src/main/java/com/example/flashcards/MainActivity.kt
package com.example.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FlashcardApp() }
    }
}

@Composable
fun FlashcardApp() {
    var index by remember { mutableStateOf(0) }
    val cards = listOf(
        "What is the capital of France?" to "Paris",
        "2 + 2 = ?" to "4",
        "Android is built on which kernel?" to "Linux"
    )

    val (q, a) = cards[index % cards.size]
    var showAnswer by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Flashcards") }) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Q: $q", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            if (showAnswer) Text("A: $a", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { showAnswer = true }) { Text("Show") }
                Button(onClick = { showAnswer = false; index++ }) { Text("Next") }
            }
        }
    }
}
