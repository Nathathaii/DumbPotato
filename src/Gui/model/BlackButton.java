package Gui.model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BlackButton extends Button {
	private final String FONT_PATH = "/resource/PressStart2P-Regular.ttf";
	private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/resource/Black_Button.png')";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/resource/Black_Button_Pressed.png');";

	public BlackButton(String text, int fontSize) {
		setText(text);
		setButtonFont(fontSize);
		setPrefWidth(256);
		setPrefHeight(64);
		setStyle(BUTTON_FREE_STYLE);
		setAlignment(Pos.CENTER);

		initializeButtonListeners();

	}

	private void setButtonFont(int fontSize) {
		setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), fontSize));
		setTextFill(Color.WHITE);
	}

	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(60);
		setLayoutY(getLayoutY() + 4);
	}

	private void setButtonReleasedStyle() {
		setStyle(BUTTON_FREE_STYLE);
		setPrefHeight(60);
		setLayoutY(getLayoutY() - 4);
	}

	private void initializeButtonListeners() {
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();
				}

			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
				}

			}
		});

		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setStyle(BUTTON_PRESSED_STYLE);
//				setEffect(new DropShadow());

			}
		});

		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setStyle(BUTTON_FREE_STYLE);
//				setEffect(null);

			}
		});
	}

}
