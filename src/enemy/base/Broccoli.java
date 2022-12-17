package enemy.base;

import Gui.GameView;
import entities.Enemy;
import entities.Potato;
import javafx.animation.Animation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import logic.LoadImage;

public class Broccoli extends Enemy implements Movable {
	private int aniIndex;
	private Image[] animations;
	private LoadImage loadImage = new LoadImage();
	private final String BROCCOLI_IMAGES = "/resource/Broccoli.png";

	public Broccoli(float x, float y) {
		super(x, y, 120, 200); // 4 32
		this.aniIndex = 0;
		this.aniTick = 0;
		this.speed = 7;
		this.counter = 0;
		this.moveCap = 50;
		this.xDrawOffset = 4;
		this.yDrawOffset = 32;
		this.aniSpeed = 5;
		initHitbox(x, y, 112, 156);
		loadAnimation();
	}

	@Override
	protected void update(int yLvlOffset) {
		this.yLvlOffset = yLvlOffset;
		updateAnimationTick();
		if (this.isHitboxOverlap(GameView.getPotato())) {
			if (GameView.getPotato().isInvincible() == false) {
				GameView.getPotato().setBloodCost(GameView.getPotato().getBloodCost() - 20);
				GameView.getPotato().setInvincible(true);

				String audioPath = ClassLoader.getSystemResource("scream.wav").toString();
				AudioClip scream = new AudioClip(audioPath);
				scream.setCycleCount(1);
				scream.setVolume(1);
				scream.play();

				Thread thread = new Thread(() -> {
					try {

						Thread.sleep(2500);
						GameView.getPotato().setInvincible(false);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				thread.start();

			}
		}
		move();
	}

	@Override
	protected void loadAnimation() {
		animations = loadImage.loadImageToArray(BROCCOLI_IMAGES, 1, 9, 120, 200)[0];

	}

	@Override
	public Image getAnimation() {
		return animations[aniIndex];
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
		if (aniIndex >= 8)
			aniIndex = 0;
	}

	@Override
	public void move() {

		this.hitbox.x += this.speed;
		this.counter++;

		if (this.counter == this.moveCap) {
			counter = 0;
			this.speed = -this.speed;
		}
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
