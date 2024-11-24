import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MapTile(val type: TileType, val occupiedBy: GameUnit? = null)
enum class TileType { PLAIN, FOREST, MOUNTAIN }
data class GameUnit(val name: String, val symbol: String)

@Composable
fun MissionScreen(
    mapData: List<List<MapTile>>,
    onTileClicked: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        mapData.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, tile ->
                    TileComposable(tile, onClick = { onTileClicked(rowIndex, colIndex) })
                }
            }
        }
    }
}

@Composable
fun TileComposable(tile: MapTile, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                when (tile.type) {
                    TileType.PLAIN -> Color.Yellow
                    TileType.FOREST -> Color.Green
                    TileType.MOUNTAIN -> Color.Gray
                }
            )
            .clickable { onClick() }
            .border(1.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        tile.occupiedBy?.let { unit ->
            Text(text = unit.symbol, fontSize = 16.sp, color = Color.White)
        }
    }
}

// Sample map initialization
val sampleMap = listOf(
    listOf(MapTile(TileType.PLAIN), MapTile(TileType.FOREST), MapTile(TileType.PLAIN), MapTile(TileType.PLAIN), MapTile(TileType.MOUNTAIN)),
    listOf(MapTile(TileType.PLAIN), MapTile(TileType.PLAIN, GameUnit("Player", "P")), MapTile(TileType.FOREST), MapTile(TileType.PLAIN), MapTile(TileType.MOUNTAIN)),
    listOf(MapTile(TileType.PLAIN), MapTile(TileType.FOREST), MapTile(TileType.PLAIN, GameUnit("Enemy", "E")), MapTile(TileType.PLAIN), MapTile(TileType.MOUNTAIN)),
    listOf(MapTile(TileType.FOREST), MapTile(TileType.PLAIN), MapTile(TileType.FOREST), MapTile(TileType.MOUNTAIN), MapTile(TileType.PLAIN)),
    listOf(MapTile(TileType.MOUNTAIN), MapTile(TileType.FOREST), MapTile(TileType.PLAIN), MapTile(TileType.PLAIN), MapTile(TileType.FOREST))
)
