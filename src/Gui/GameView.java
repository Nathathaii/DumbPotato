package Gui;

import Gui.model.BlackButton;
import Gui.model.HpBar;
import Gui.model.MyLabel;
import Gui.model.MySubScene;
import Gui.model.RedButton;
import Item.Heart;
import entities.ElementManager;
import entities.Potato;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import logic.LoadImage;
import tiles.TileManager;

public class GameView {
	private static AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;

	private Canvas canvas;
	private GraphicsContext gc;

	public static final int GAME_WIDTH = 1152; // will be reassigned in tile ratio later
	public static final int GAME_HEIGHT = 640;
	public static final int TILES_IN_WIDTH = 18;
	public static final int TILES_IN_HEIGHT = 10;
	public static final int TILES_SIZE = 64;
	public static String BACKGROUND = "/resource/Game_Background.png";

	private Heart heart;
	private HpBar hpBar;
	private static Potato potato;
	private ElementManager elementManager;
	private Stage menuStage;
	private AnimationTimer gameTimer;
	private static TileManager tileManager;
	private int score = 0;
	private MyLabel scoreText;
	private static AudioClip themeSong;
	private static AudioClip endSong;

	public GameView() {
		initializeStage();
		createPotato();
		createKeyListener();
		createHeart();
		createHpBar();
		createScoreText();
		playThemeSong();
	}

	private void createScoreText() {
		scoreText = new MyLabel("SCORE : " + score, 20);
		scoreText.setLayoutX(50);
		scoreText.setLayoutY(100);
		gamePane.getChildren().add(scoreText);
	}

	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setTitle("Dumb Potato game is running");
		gameStage.setScene(gameScene);
		score = 0;

		canvas = new Canvas(1152, 640);
		gc = canvas.getGraphicsContext2D();
		gamePane.getChildren().add(canvas);
	}

	private void createPotato() {
		potato = new Potato(550, 300, 120, 200); // width and height should x Game scale
		tileManager = new TileManager(this);
		potato.loadLvlData(tileManager.getCurrentTile().getLevelData()); // NEWLYYYYYYYYYYYY!!!!
		elementManager = new ElementManager(this, potato);

	}

	public void getPoint() {
		score++;
		scoreText.setText("SCORE : " + score);
	}

	private void createHeart() {
		heart = new Heart(700, 500);

		tileManager = new TileManager(this);
		heart.loadLvlData(tileManager.getCurrentTile().getLevelData());
	}

	private void createHpBar() {
		hpBar = new HpBar(512, 128, 0, 0);

		gamePane.getChildren().add(hpBar);
	}

	public void createNewGame(Stage menuStage) { // will be called by MenuView

		this.menuStage = menuStage;
		this.menuStage.hide();

		createBackground();
		createGameLoop();
		gameStage.show();
		drawTiles(gc);

	}

	private void createBackground() {
		Image backgroundImage = new Image(BACKGROUND, 1152, 640, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}

	public static void playThemeSong() {
		String audioPath = ClassLoader.getSystemResource("GameViewAud2.wav").toString();
		AudioClip themeSong = new AudioClip(audioPath);
		themeSong.setCycleCount(Animation.INDEFINITE);
		themeSong.setVolume(0.5);
		GameView.setThemeSong(themeSong);
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

	public static void playEndSong() {
		String audioPath = ClassLoader.getSystemResource("EndViewAud.wav").toString();
		AudioClip endSong = new AudioClip(audioPath);
		themeSong.setCycleCount(Animation.INDEFINITE);
		themeSong.setVolume(1);
		GameView.setEndSong(endSong);
		Thread thread0 = new Thread(() -> {
			try {
				Platform.runLater(new Runnable() {
					public void run() {
						endSong.play();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		thread0.start();
	}

	private void drawTiles(GraphicsContext gc) {
		tileManager = new TileManager(this);
		tileManager.draw(gc, GameView.getPotato().getyLvlOffset());
	}

	public void update() {
		potato.update();
		hpBar.update();
		elementManager.update(GameView.getPotato().getyLvlOffset());
	}

	private void render(GraphicsContext gc) {
		clearPane(gc);
		drawTiles(gc);
		potato.draw(gc);
		elementManager.draw(gc);
	}

	private void clearPane(GraphicsContext gc2) {
		gc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
	}

	private void createGameLoop() {
		gameTimer = new AnimationTimer() { /////// Animation timer

			@Override
			public void handle(long now) {
				update();
				render(gc);
				checkEndgame(); /////////
			}

		};
		gameTimer.start();

	}

	private void checkEndgame() {
		if (potato.isDeath()) {
			gameTimer.stop();

			String audioPath = ClassLoader.getSystemResource("cartoon-scream.mp3").toString();
			AudioClip scream = new AudioClip(audioPath);
			scream.setCycleCount(1);
			scream.setVolume(1);
			scream.play();

			GameView.getThemeSong().stop();
			GameView.playEndSong();

			MySubScene endScene = new MySubScene();
			gamePane.getChildren().add(endScene);

			MyLabel gameOver = new MyLabel("GAME OVER", 40);
			gameOver.setLayoutX(110);
			gameOver.setLayoutY(50);
			endScene.getPane().getChildren().add(gameOver);

			MyLabel allScore = new MyLabel("TOTAL SCORE : " + score, 20);
			allScore.setLayoutX(140);
			allScore.setLayoutY(130);
			endScene.getPane().getChildren().add(allScore);

			BlackButton menuButton = new BlackButton("RESTART", 15);
			menuButton.setLayoutX(175);
			menuButton.setLayoutY(200);
			endScene.getPane().getChildren().add(menuButton);
			menuButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					String audioPath = ClassLoader.getSystemResource("button.mp3").toString();
					AudioClip buttonSound = new AudioClip(audioPath);
					buttonSound.setCycleCount(1);
					buttonSound.setVolume(1);
					buttonSound.play();

					GameView.getEndSong().stop();
					GameView gameView2 = new GameView();
					gameView2.createNewGame(gameStage);
				}

			});
			RedButton exitButton = new RedButton("EXIT", 15);
			exitButton.setLayoutX(175);
			exitButton.setLayoutY(283);
			endScene.getPane().getChildren().add(exitButton);
			exitButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					String audioPath = ClassLoader.getSystemResource("button.mp3").toString();
					AudioClip buttonSound = new AudioClip(audioPath);
					buttonSound.setCycleCount(1);
					buttonSound.setVolume(1);
					buttonSound.play();

					GameView.getEndSong().stop();
					gameStage.close();
				}

			});

		}
	}

	private void createKeyListener() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.LEFT) {
					potato.setLeft(true);

				} else if (event.getCode() == KeyCode.RIGHT) {
					potato.setRight(true);

				} else if (event.getCode() == KeyCode.UP) {
					potato.setUp(true);

				} else if (event.getCode() == KeyCode.DOWN) {
					potato.setDown(true);

				}

			}
		});

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// potato.setIdleImage();
				if (event.getCode() == KeyCode.LEFT) {
					potato.setLeft(false);

				} else if (event.getCode() == KeyCode.RIGHT) {
					potato.setRight(false);

				} else if (event.getCode() == KeyCode.UP) {
					potato.setUp(false);

				} else if (event.getCode() == KeyCode.DOWN) {
					potato.setDown(false);

				}

			}
		});

	}

	public static AnchorPane getGamePane() {
		return gamePane;
	}

	public void setGamePane(AnchorPane gamePane) {
		this.gamePane = gamePane;
	}

	public Scene getGameScene() {
		return gameScene;
	}

	public void setGameScene(Scene gameScene) {
		this.gameScene = gameScene;
	}

	public Stage getGameStage() {
		return gameStage;
	}

	public void setGameStage(Stage gameStage) {
		this.gameStage = gameStage;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	public static Potato getPotato() {
		return potato;
	}

	public void setPotato(Potato potato) {
		this.potato = potato;
	}

	public Stage getMenuStage() {
		return menuStage;
	}

	public void setMenuStage(Stage menuStage) {
		this.menuStage = menuStage;
	}

	public AnimationTimer getGameTimer() {
		return gameTimer;
	}

	public void setGameTimer(AnimationTimer gameTimer) {
		this.gameTimer = gameTimer;
	}

	public static TileManager getTileManager() {
		return tileManager;
	}

	public static void setTileManager(TileManager tileManager) {
		GameView.tileManager = tileManager;
	}

	public static AudioClip getThemeSong() {
		return themeSong;
	}

	public static void setThemeSong(AudioClip themeSong) {
		GameView.themeSong = themeSong;
	}

	public static AudioClip getEndSong() {
		return endSong;
	}

	public static void setEndSong(AudioClip endSong) {
		GameView.endSong = endSong;
	}

}
