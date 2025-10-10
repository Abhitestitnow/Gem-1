package com.example.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.flashcards.data.Flashcard
import com.example.flashcards.data.FlashcardGroup
import com.example.flashcards.ui.theme.FlashCardsTheme
import com.example.flashcards.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashCardsTheme {
                FlashcardsApp()
            }
        }
    }
}

@Composable
fun FlashcardsApp(viewModel: FlashcardViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold { padding ->
        NavHost(
            navController = navController,
            startDestination = "library",
            modifier = Modifier.padding(padding)
        ) {
            composable("library") {
                LibraryScreen(viewModel, navController)
            }
            composable(
                route = "group/{groupId}",
                arguments = listOf(navArgument("groupId") { type = NavType.IntType })
            ) { backStackEntry ->
                val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
                GroupDetailScreen(groupId, viewModel, navController)
            }
        }
    }
}

@Composable
fun LibraryScreen(viewModel: FlashcardViewModel, navController: NavHostController) {
    val groups by viewModel.groups.collectAsState()

    var newGroupName by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Groups", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = newGroupName,
            onValueChange = { newGroupName = it },
            label = { Text("New Group Name") }
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            if (newGroupName.isNotBlank()) {
                viewModel.addGroup(newGroupName)
                newGroupName = ""
            }
        }) {
            Text("Add Group")
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(groups) { group ->
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("group/${group.id}") }
                        .padding(8.dp)
                )
                Divider()
            }
        }
    }
}

@Composable
fun GroupDetailScreen(
    groupId: Int,
    viewModel: FlashcardViewModel,
    navController: NavHostController
) {
    val flashcardsState = viewModel.getFlashcardsForGroup(groupId).collectAsState(emptyList())
    val flashcards = flashcardsState.value

    var showAddCard by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Flashcards", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Button(onClick = { showAddCard = true }) {
            Text("Add Flashcard")
        }

        if (showAddCard) {
            AddFlashcardDialog(
                onDismiss = { showAddCard = false },
                onAddFlashcard = { question, answer ->
                    viewModel.addFlashcard(question, answer, groupId)
                    showAddCard = false
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(flashcards) { flashcard ->
                FlashcardItem(flashcard, onDelete = { viewModel.deleteFlashcard(it) })
                Divider()
            }
        }
    }
}

@Composable
fun FlashcardItem(flashcard: Flashcard, onDelete: (Flashcard) -> Unit) {
    var flipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (flipped) 180f else 0f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .clipToBounds()
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density
            }
            .clickable { flipped = !flipped },
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(flashcard.question, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = { onDelete(flashcard) }) {
                    Text("Delete")
                }
            }
        } else {
            Box(Modifier.graphicsLayer { rotationY = 180f }) {
                Text(flashcard.answer, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@Composable
fun AddFlashcardDialog(
    onDismiss: () -> Unit,
    onAddFlashcard: (String, String) -> Unit
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Flashcard") },
        text = {
            Column {
                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text("Question") }
                )
                OutlinedTextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text("Answer") },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (question.isNotBlank() && answer.isNotBlank()) {
                        onAddFlashcard(question, answer)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
