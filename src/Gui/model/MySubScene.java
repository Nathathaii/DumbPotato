package Gui.model;

import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

public class MySubScene extends SubScene {

	private final static String BACKGROUND_IMAGE = "/resource/Subscene.png";

	public MySubScene() {
		super(new AnchorPane(), 640, 480);
		prefWidth(640);
		prefHeight(480);

		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 400, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

		setLayoutX(256);
		setLayoutY(80);

		AnchorPane root = (AnchorPane) this.getRoot();
		root.setBackground(new Background(image));

	}

	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

}
