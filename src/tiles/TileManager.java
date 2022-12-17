package tiles;

import Gui.GameView;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import logic.LoadImage;

public class TileManager {

	private Image[] tileArray;
	private final String TILE_PATH = "/resource/Tiles.png";
	private GameView gameView;
	private Tile myTile;
	private LoadImage load;

	public TileManager(GameView gameView) {
		loadTiles();
		this.gameView = gameView;
		load = new LoadImage();
		myTile = new Tile(load.GetLevelData());
	}

	private void loadTiles() {
		LoadImage load = new LoadImage();
		Image[][] Array2D = load.loadImageToArray(TILE_PATH, 1, 9, GameView.TILES_SIZE, GameView.TILES_SIZE);
		tileArray = Array2D[0];
	}

	public void update() {

	}

	public void draw(GraphicsContext gc, int lvlOffset) { ///
//		gc.drawImage(tileArray[3], 0, 0);
		for (int j = 0; j < myTile.getLevelData().length; j++)
			for (int i = 0; i < GameView.TILES_IN_WIDTH; i++) {
				int index = myTile.getSpriteIndex(i, j);
				gc.drawImage(tileArray[index], GameView.TILES_SIZE * i, GameView.TILES_SIZE * j - lvlOffset,
						GameView.TILES_SIZE, GameView.TILES_SIZE);

			}
	}

	public Tile getCurrentTile() {
		return this.myTile;
	}

}
