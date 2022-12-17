package Main;

import Gui.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			MenuView menuView = new MenuView();
			primaryStage = menuView.getMainStage();
			primaryStage.setTitle("Dump Potato");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
