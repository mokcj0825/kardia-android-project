import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainMenuScreen(
    navController: NavController,
    onStartGame: () -> Unit,
    onLoadGame: () -> Unit,
    onSettings: () -> Unit
) {
    val context = LocalContext.current;
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Your Game Title",
            fontSize = 36.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(onClick = onStartGame) {
            Text("Start New Game")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (GameState.loadGame(context)) {
                navController.navigate("missionScreen/${GameState.getCurrentMission()}")
            } else {
                Toast.makeText(context, "No saved game found", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Load Game")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSettings) {
            Text("Settings")
        }
    }
}
