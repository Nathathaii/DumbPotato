package Gui.model;

import Gui.GameView;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HpBar extends StackPane {
	private int width;
	private int height;
	private int x;
	private int y;
	private ImageView imgHpBar;
	private Rectangle rect;

	public HpBar(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.setPrefWidth(width);
		this.setPrefHeight(height);
		Image img = new Image("/resource/Hp_Bar.png");
		this.imgHpBar = new ImageView(img);
		this.rect = new Rectangle(420, 32);// 420,32

		// rect.setLayoutX(46);

		this.rect.setFill(Color.PALEVIOLETRED);
		this.getChildren().addAll(rect, this.imgHpBar);
		// rect.setX(0);
		this.setAlignment(Pos.CENTER_LEFT);
		rect.setTranslateX(48);
		// rect.setLayoutX(0);
	}

	public void update() {
		double newWidth = GameView.getPotato().getBloodCost() * 4.2;
		this.rect.setWidth(newWidth);
	}

}
