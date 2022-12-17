package Gui.model;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class MyLabel extends Label {
	private final String FONT_PATH = "/resource/PressStart2P-Regular.ttf";

	public MyLabel(String text, int size) {
		setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), size));
		setText(text);
	}
}
