package entities;

import java.util.ArrayList;

import Gui.GameView;
import Item.Heart;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import logic.LoadImage;

public class ElementManager {
	private ArrayList<Enemy> enemyList;
	private ArrayList<Heart> heartList;
	private LoadImage load = new LoadImage();
	private Potato potato;
	private GameView gameView;

	public ElementManager(GameView gameView, Potato potato) {
		this.potato = potato;
		this.gameView = gameView;
		loadList();
	}

	public void update(int yLvlOffset) {
		updateEnemy(yLvlOffset);
		updateHeart(yLvlOffset);
	}

	public void draw(GraphicsContext gc) {
		drawEnemy(gc);
		drawHeart(gc);
	}

	private void drawHeart(GraphicsContext gc) {
		for (Enemy e : enemyList) {
			e.draw(gc);
		}
	}

	private void drawEnemy(GraphicsContext gc) {
		for (Heart h : heartList) {
			h.draw(gc);
		}
	}

	private void updateHeart(int yLvlOffset) {
		int j = -1;
		for(int i = 0;i<heartList.size();i++) {
			heartList.get(i).update(yLvlOffset);

			if (potato.isHitboxOverlap(heartList.get(i))) {
				String audioPath = ClassLoader.getSystemResource("collectHeart.mp3").toString();
		        AudioClip collectHeart = new AudioClip(audioPath);
		        collectHeart.setCycleCount(1);
		        collectHeart.setVolume(2);
		        collectHeart.play();
		        
				potato.setBloodCost(100);
				gameView.getPoint();
				j = i;
			}
		}
		if(j!= -1) {
			heartList.remove(j);
		}
	}

	public void updateEnemy(int yLvlOffset) {
		for (Enemy e : enemyList) {
			e.update(yLvlOffset);
		}
	}

	private void loadList() {
		enemyList = load.getEnemy();
		heartList = load.getHeart();
	}

}
