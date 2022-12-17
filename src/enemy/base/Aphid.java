package enemy.base;

import Gui.GameView;
import entities.Enemy;
import entities.Potato;
import javafx.animation.Animation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import logic.LoadImage;

public class Aphid extends Enemy implements Movable {

	private final String APHID_IMAGES = "/resource/Aphid.png";
	private LoadImage loadImage = new LoadImage();
	private Image[] animations;

	public Aphid(float x, float y) {
		super(x, y, 128, 128);
		this.aniIndex = 0;
		this.aniTick = 0;
		this.speed = 7;
		this.counter = 0;
		this.moveCap = 100;
		initHitbox(x, y, 56, 56);
		this.xDrawOffset = 44;
		this.yDrawOffset = 36;
		loadAnimation();
	}

	@Override
	public void move() {
		this.hitbox.x += this.speed;
		this.hitbox.y -= this.speed;
		this.counter++;

		if (this.counter == this.moveCap) {
			counter = 0;
			this.speed = -this.speed;
		}
	}

	@Override
	protected void update(int yLvlOffset) {
		updateAnimationTick();
		this.yLvlOffset = yLvlOffset;

		if (this.isHitboxOverlap(GameView.getPotato())) {
			if (GameView.getPotato().isInvincible() == false) {
				GameView.getPotato().setBloodCost(GameView.getPotato().getBloodCost() - 10);
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
		animations = loadImage.loadImageToArray(APHID_IMAGES, 1, 8, 128, 128)[0];
	}

	@Override
	public Image getAnimation() {
		return animations[aniIndex];
	}

	@Override
	protected void updateAnimationTick() { // for blood decreasing only
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
		if (aniIndex >= 7)
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
