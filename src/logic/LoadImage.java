package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Gui.GameView;
import Item.Heart;
import enemy.base.Aphid;
import enemy.base.Broccoli;
import enemy.base.Rafflesia;
import entities.Enemy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class LoadImage {

	public static final String All_TILES_DATA = "/resource/Try_Render.png";

	public LoadImage() { // loadImageToArray can't turn into static because of getClass in loading method

	}

	private static Image convertToFxImage(BufferedImage image) {
		WritableImage wr = null;
		if (image != null) {
			wr = new WritableImage(image.getWidth(), image.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					pw.setArgb(x, y, image.getRGB(x, y));
				}
			}
		}

		return new ImageView(wr).getImage();
	}

	public Image[][] loadImageToArray(String path, int row, int col, int width, int height) {

		Image[][] ImageArray = new Image[row][col];

		BufferedImage originalImage = normalLoad(path);

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Image img = convertToFxImage(originalImage.getSubimage(j * width, i * height, width, height));

				ImageArray[i][j] = img;
			}
		}

		return ImageArray;
	}

	public BufferedImage normalLoad(String path) {
		BufferedImage image = null;
		try {
			InputStream is = getClass().getResourceAsStream(path);
			image = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	// -----------get data from COLOR-------------------
	// get tiles
	public int[][] GetLevelData() {
		BufferedImage img = normalLoad(All_TILES_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed(); // red for tiles
				if (value >= 9)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}

	// get enemies
	public ArrayList<Enemy> getEnemy() {
		BufferedImage img = normalLoad(All_TILES_DATA);
		ArrayList<Enemy> enemyList = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen(); // green for enemy
				if (value == 1) {// broccoli
					enemyList.add(new Broccoli(i * GameView.TILES_SIZE, j * GameView.TILES_SIZE));
				} else if (value == 2) { // aphid
					enemyList.add(new Aphid(i * GameView.TILES_SIZE, j * GameView.TILES_SIZE));

				} else if (value == 3) {// rafflesia
					enemyList.add(new Rafflesia(i * GameView.TILES_SIZE, j * GameView.TILES_SIZE));
				}
			}
		}
		return enemyList;
	}

	// get Heart
	public ArrayList<Heart> getHeart() {
		BufferedImage img = normalLoad(All_TILES_DATA);
		ArrayList<Heart> heartList = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue(); // blue for heart
				int otherValue = color.getRed();
				if (value == 0 && otherValue != 0) {
					heartList.add(new Heart(i * GameView.TILES_SIZE, j * GameView.TILES_SIZE));
				}
			}
		}
		return heartList;
	}

}
