package enemy.base;

import Gui.GameView;
import entities.Enemy;
import entities.Potato;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import logic.LoadImage;

public class Rafflesia extends Enemy {

	protected int dangerWidth;
	protected int dangerHeight;
	private Image[] animations;
	private LoadImage loadImage = new LoadImage();
	private final String RAFFLESIA_IMAGES = "/resource/Rafflesia.png";

	public Rafflesia(float x, float y) {
		super(x, y, 256, 256); // 20 96
		this.aniIndex = 0;
		this.aniTick = 0;
		initHitbox(x, y, 196, 152);
		this.xDrawOffset = 20;
		this.yDrawOffset = 96;
		loadAnimation();
	}

	@Override
	protected void loadAnimation() {
		animations = loadImage.loadImageToArray(RAFFLESIA_IMAGES, 1, 5, 256, 256)[0];
	}

	@Override
	public Image getAnimation() {
		return animations[aniIndex];
	}

	@Override
	protected void update(int yLvlOffset) {
		updateAnimationTick();
		if (this.isHitboxOverlap(GameView.getPotato())) {
			GameView.getPotato().setBloodCost(0);

			String audioPath = ClassLoader.getSystemResource("cartoon-scream.mp3").toString();
			AudioClip scream = new AudioClip(audioPath);
			scream.setCycleCount(1);
			scream.setVolume(1);
			scream.play();
		}
		this.yLvlOffset = yLvlOffset;
	}

	@Override
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= this.aniSpeed) {
			aniTick = 0;
		}
		if (aniTick == 0)
			updateAniIndex();
	}

	@Override
	protected void updateAniIndex() {
		aniIndex++;
		if (aniIndex >= 4)
			aniIndex = 0;
	}

	// -----------------set and get-----------------
	public int getAniIndex() {
		return aniIndex;
	}

	public float getx() {
		return hitbox.x - xDrawOffset;
	}

	public float gety() {
		return (hitbox.y - yDrawOffset) - yLvlOffset;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(getAnimation(), this.getx(), this.gety());
	}

}
