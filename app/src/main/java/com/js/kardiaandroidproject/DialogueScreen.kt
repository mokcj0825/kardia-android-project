import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import com.google.gson.Gson
import java.io.InputStreamReader

data class DialogueLine(val character: String, val text: String, val choices: List<String>? = null)

@Composable
fun DialogueScreen(
    scriptId: String,
    onChoiceSelected: (Int) -> Unit,
    onNavigateToLogs: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToMainMenu: () -> Unit,
    onSaveGame: () -> Unit
) {
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }
    var dialogueLine by remember { mutableStateOf<DialogueLine?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(scriptId) {
        try {
            dialogueLine = loadDialogueFromAssets(context, "dialogue-$scriptId.json")
        } catch (e: Exception) {
            error = "Failed to load dialogue: ${e.message}"
        }
    }

    if (error != null) {
        Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
    } else if (dialogueLine != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dialogueLine!!.character,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = dialogueLine!!.text,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            dialogueLine!!.choices?.forEachIndexed { index, choice ->
                Button(onClick = { onChoiceSelected(index) }) {
                    Text(choice)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(onClick = onSaveGame) {
                Text("Save Game")
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

fun loadDialogueFromAssets(context: Context, fileName: String): DialogueLine {
    val assetManager = context.assets
    assetManager.open(fileName).use { inputStream ->
        InputStreamReader(inputStream).use { reader ->
            return Gson().fromJson(reader, DialogueLine::class.java)
        }
    }
}