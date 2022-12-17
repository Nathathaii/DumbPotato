package logic;

import Gui.GameView;

public class MovableCoordinate {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	public static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxHeight = lvlData.length * GameView.TILES_SIZE;
		if (x < 0 || x >= GameView.GAME_WIDTH)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;

		float xIndex = x / GameView.TILES_SIZE;
		float yIndex = y / GameView.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value == 1 || value == 2 || value == 5 || value == 6 || value == 7 || value == 8) {
			return true;
		}
		return false;
//		if (value >= 9 || value < 0 || value != 0 ) // ?????? || value != 11)->tile's coordinate?
//			return true;
//		return false;
	}
}
