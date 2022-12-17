package Gui;

import Gui.model.BlackButton;
import Gui.model.MyLabel;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class MenuView {

	private static final int HEIGHT = 640;
	private static final int WIDTH = 1152;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private static AudioClip themeSong;

	private final static int MENU_BUTTON_START_X = 448;
	private final static int MENU_BUTTON_START_Y = 320;

	public MenuView() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createGameName();
		createStartButton();
		createBackGround();
		playThemeSong();

	}

	private void createBackGround() {
		Image backgroundImage = new Image("/resource/Menu_Background.png", 1152, 640, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}

	private void createGameName() {
		MyLabel gameName = new MyLabel("DUMB POTATO", 80);
		gameName.setLayoutX(140);
		gameName.setLayoutY(100);
		mainPane.getChildren().add(gameName);

	}

	private void createStartButton() {
		BlackButton startButton = new BlackButton("START", 20);
		startButton.setLayoutX(MENU_BUTTON_START_X);
		startButton.setLayoutY(MENU_BUTTON_START_Y);

		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String audioPath = ClassLoader.getSystemResource("button.mp3").toString();
				AudioClip buttonSound = new AudioClip(audioPath);
				buttonSound.setCycleCount(1);
				buttonSound.setVolume(1);
				buttonSound.play();
				MenuView.getThemeSong().stop();

				GameView gameView = new GameView();
				gameView.createNewGame(mainStage);
			}

		});
		mainPane.getChildren().add(startButton);
	}

	public static void playThemeSong() {
		String audioPath = ClassLoader.getSystemResource("MenuViewAud.wav").toString();
		AudioClip themeSong = new AudioClip(audioPath);
		themeSong.setCycleCount(Animation.INDEFINITE);
		themeSong.setVolume(1);
		MenuView.setThemeSong(themeSong);
		Thread thread0 = new Thread(() -> {
			try {
				Platform.runLater(new Runnable() {
					public void run() {
						themeSong.play();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		thread0.start();
	}

	public Stage getMainStage() {
		return mainStage;
	}

	public static AudioClip getThemeSong() {
		return themeSong;
	}

	public static void setThemeSong(AudioClip themeSong) {
		MenuView.themeSong = themeSong;
	}

}
