import android.content.Context
import android.content.SharedPreferences

object GameState {
    private const val PREF_NAME = "game_save"
    private const val KEY_MISSION = "current_mission"
    private const val KEY_PLAYER_LEVEL = "player_level"

    private var currentMission: Int = 1
    private var playerLevel: Int = 1

    fun saveGame(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putInt(KEY_MISSION, currentMission)
            putInt(KEY_PLAYER_LEVEL, playerLevel)
            apply()
        }
    }

    fun loadGame(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (prefs.contains(KEY_MISSION)) {
            currentMission = prefs.getInt(KEY_MISSION, 1)
            playerLevel = prefs.getInt(KEY_PLAYER_LEVEL, 1)
            return true // Indicates that a save was found and loaded
        }
        return false // No save data found
    }

    fun clearSave(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun getCurrentMission(): Int = currentMission
    fun getPlayerLevel(): Int = playerLevel

    // Update the game state when progressing in the game
    fun updateMission(mission: Int) {
        currentMission = mission
    }

    fun updatePlayerLevel(level: Int) {
        playerLevel = level
    }
}
