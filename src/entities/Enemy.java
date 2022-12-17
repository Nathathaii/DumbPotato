package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Enemy extends Entity {

	protected int speed;
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 20;
	protected int moveCap;
	protected int counter;
	protected int yLvlOffset = 0;
	protected float xDrawOffset;
	protected float yDrawOffset;

	public Enemy(float x, float y, int width, int height) {
		super(x, y, width, height);

	}

	protected abstract void update(int yLvlOffset);

	protected abstract void updateAnimationTick();

	protected abstract void updateAniIndex();

	// protected abstract int attack(Potato potato);
	protected abstract void loadAnimation();

	public abstract Image getAnimation();

	public abstract void draw(GraphicsContext gc);

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
