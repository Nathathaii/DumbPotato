package entities;

import java.awt.geom.Rectangle2D;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	private float xDrawOffset;
	private float yDrawOffset;
	protected Rectangle2D.Float hitbox;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	protected void drawHitbox(GraphicsContext gc) {
		// For debugging the hitbox

		gc.setFill(javafx.scene.paint.Color.PINK);
		gc.rect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

	}

	protected void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);

	}
	protected void updateHitbox() {
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isHitboxOverlap(Entity e) {

		if (this.hitbox.intersects(e.hitbox))
			return true;
		return false;
	}

	public abstract void draw(GraphicsContext gc);

}
