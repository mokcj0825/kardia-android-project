package com.js.kardiaandroidproject

import DialogueLine
import DialogueScreen
import MainMenuScreen
import MissionScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sampleMap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavigation()
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainMenu") {
        composable("mainMenu") {
            MainMenuScreen(
                navController = navController,
                onStartGame = {
                    GameState.updateMission(1) // Reset to first mission on new game
                    //navController.navigate("missionScreen/1")
                    navController.navigate("dialogueScreen/1")
                },
                onLoadGame = { navController.navigate("dialogueScreen") },  // Placeholder
                onSettings = { /* Future implementation */ }
            )
        }
        composable("dialogueScreen/{scriptId}") { backStackEntry ->
            val context = LocalContext.current
            val scriptId = backStackEntry.arguments?.getString("scriptId")

            if (scriptId != null) {
                DialogueScreen(
                    scriptId = scriptId,
                    onChoiceSelected = { choiceIndex ->
                        if (choiceIndex == 0) navController.navigate("missionScreen/1")
                        else navController.popBackStack()
                    },
                    onNavigateToLogs = { /* Navigate to logs screen */ },
                    onNavigateToSettings = { /* Navigate to settings screen */ },
                    onNavigateToMainMenu = { navController.navigate("mainMenu") },
                    onSaveGame = { GameState.saveGame(context) }
                )
            } else {
                throw IllegalArgumentException("Invalid scriptId")
            }
        }
        composable("missionScreen/{missionId}") { backStackEntry ->
            val missionId = backStackEntry.arguments?.getString("missionId")?.toIntOrNull()
            if(missionId != null) {
                MissionScreen(
                    mapData = sampleMap,
                    onTileClicked = { row, col ->
                        // Logic for tile clicks, e.g., move a unit or attack
                    }
                )
            } else {
                throw IllegalArgumentException("Invalid missionId")
            }
        }
    }
}