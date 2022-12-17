package entities;

import java.awt.geom.Rectangle2D.Float;

import Gui.GameView;
import constants.State;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.LoadImage;
import logic.MovableCoordinate;

public class Potato extends Entity {
	private int bloodCost;
	private State state;
	private int walkingSpeed;
	private boolean left, right, up, down;
	private boolean moving = false;
	private int[][] lvlData;
	private float xDrawOffset = 24; //
	private float yDrawOffset = 60;

	private int yLvlOffset;
	private int upBorder = (int) (0.5 * GameView.GAME_HEIGHT);
	private int downBorder = (int) (0.5 * GameView.GAME_HEIGHT);
	LoadImage loadImage = new LoadImage();
	private int lvlTilesHeight = loadImage.GetLevelData().length; // how to find in vertical??
	private int maxTilesOffset = lvlTilesHeight - GameView.TILES_IN_HEIGHT;
	private int maxTilesOffsetY = maxTilesOffset * GameView.TILES_SIZE;
	private final String POTATO_IMAGES = "/resource/Normal_+_Unhealthy.png";
	private final String DIED_IMAGES = "/resource/Crashing_Death.png";
	private int aniIndex;
	private int aniTick, bloodTimer = 10;
	private int playerAction = 0;
	private Image[][] animations;
	private Image[] deathAnimations;
	private int dyingTime = 0;
	private boolean death = false;
	private boolean invincible; // Ptt

	public Potato(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.setBloodCost(100);
		this.setState(State.NORMAL_IDLE);
		this.setWalkingSpeed(10);
		loadAnimation();

		aniIndex = 0;
		aniTick = 0;
		initHitbox(x, y, 60, 100);
	}

	private void loadAnimation() {
		animations = loadImage.loadImageToArray(POTATO_IMAGES, 4, 6, 120, 200);
		deathAnimations = loadImage.loadImageToArray(DIED_IMAGES, 1, 12, 120, 200)[0];
	}

	public Image getAnimation() {
		if (state == State.DIED) {
			return deathAnimations[dyingTime];
		}
		return animations[playerAction][aniIndex];
	}

	public void update() { // call this method in GameView (loop)
		updatePos();
		updateState();
		GameView.getTileManager().update();
		if (aniTick == 0) {
			decreasePotatoBlood();
			updateAniIndex();
		}
		checkCloseToBorder();
		updateAnimationTick();
		checkDying();
	}

	private void checkDying() {
		if (bloodCost <= 0) {
			state = State.DIED;
			dyingTime++;
			if (dyingTime >= 11) {
				death = true;
			}

		}
	}

	public boolean isDeath() {
		return death;
	}

	private void updateAnimationTick() { // for blood decreasing only
		aniTick++;
		if (aniTick >= bloodTimer) {
			aniTick = 0;
		}
	}

	private void decreasePotatoBlood() {
		this.bloodCost -= 1;
	}

	private void updateAniIndex() {
		aniIndex++;
		if (aniIndex >= 5)
			aniIndex = 0;
	}

	private void checkCloseToBorder() { // check บรล่างไปได้แต่อย่าเกินขอบยาว ซ้ายขวาห้ามไป
		int potatoY = (int) this.getHitbox().y;
		int diff = potatoY - yLvlOffset;

		if (diff > downBorder) { // Should be 0????w,j
			yLvlOffset += diff - downBorder;
		} else if (diff < upBorder) { // or diff ? down????
			yLvlOffset += diff - upBorder;
		}
		if (yLvlOffset > maxTilesOffsetY) {
			yLvlOffset = maxTilesOffsetY;
		} else if (yLvlOffset < 0) {
			yLvlOffset = 0;
		}
	}

	private void updatePos() {
		moving = false;
		if ((!left && !right && !up && !down) || (right && left))
			return;

		float xSpeed = 0, ySpeed = 0;

		if (left && !right)
			xSpeed = -walkingSpeed;
		else if (right && !left)
			xSpeed = walkingSpeed;

		if (up && !down)
			ySpeed = -walkingSpeed;
		else if (down && !up)
			ySpeed = walkingSpeed;

		if (MovableCoordinate.CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}

	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	private void updateState() {
		if (this.bloodCost < 50) {
			if (moving) {
				state = State.EXHAUSTED_WALKING;
				this.playerAction = 3;
			} else {
				state = State.EXHAUSTED_IDLE;
				this.playerAction = 2;
			}
		} else {
			if (moving) {
				state = State.NORMAL_WALKING;
				this.playerAction = 1;
			} else {
				state = State.NORMAL_IDLE;
				this.playerAction = 0;
			}
		}

	}

	public void setLeft(boolean l) {
		this.left = l;
	}

	public void setRight(boolean r) {
		this.right = r;
	}

	public void setUp(boolean u) {
		this.up = u;
	}

	public void setDown(boolean d) {
		this.down = d;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(getAnimation(), this.getx(), this.gety());
	}

	// ---------------get and set---------------
	public int getBloodCost() {
		return bloodCost;
	}

	public void setBloodCost(int bloodCost) {
		if (this.bloodCost < 0)
			this.bloodCost = 0;
		this.bloodCost = bloodCost;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getWalkingSpeed() {
		return walkingSpeed;
	}

	public void setWalkingSpeed(int walkingSpeed) {
		if (this.walkingSpeed < 0)
			this.walkingSpeed = 0;
		this.walkingSpeed = walkingSpeed;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public void setAniIndex(int aniIndex) {
		this.aniIndex = aniIndex;
	}

	public int getPlayerAction() {
		return playerAction;
	}

	public void setPlayerAction(int playerAction) {
		this.playerAction = playerAction;
	}

	public Float getHitBox() {
		return hitbox;
	}

	public float getx() {
		return (hitbox.x - xDrawOffset);
	}

	public float gety() {
		return (hitbox.y - yDrawOffset) - yLvlOffset;
	}

	public boolean isInvincible() {
		return this.invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public int getyLvlOffset() {
		return yLvlOffset;
	}

	public void setyLvlOffset(int yLvlOffset) {
		this.yLvlOffset = yLvlOffset;
	}

////---------------------------------------------

}