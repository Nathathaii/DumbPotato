package Item;

import Gui.GameView;
import entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Heart extends Entity {

	private Image imgHeart;
	private int yLvlOffset;
	private int[][] lvlData;
	private int xDrawOffset = 3;
	private int yDrawOffset = 9;

	public Heart(float x, float y) {
		super(x, y, 48, 48);
		initHitbox(x, y, width, height);
		imgHeart = new Image("/resource/Heart.png");
		this.yLvlOffset = 0;
	}

	public float getx() {
		return (hitbox.x - xDrawOffset);
	}

	public float gety() {
		return (hitbox.y - yDrawOffset) - yLvlOffset;
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(imgHeart, this.getx(), this.gety());
	}

	public void update(int yLvlOffset) {
		this.yLvlOffset = yLvlOffset;
	}

}
