package de.basket.application;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import de.basket.business.BackgroundMusikPlayer;
import de.basket.business.GamePlayer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import de.basket.scenes.controller.HomepageViewController;
import de.basket.scenes.controller.LevelViewController;
import de.basket.scenes.controller.OptionsViewController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;



public class Main extends Application {
	public Stage primaryStage;

	private Map<String, Pane> scenes;

	private BackgroundMusikPlayer backgroundplayer;
	private GamePlayer gamePlayer;
	
	public void init() {
		backgroundplayer = new BackgroundMusikPlayer();
		gamePlayer = new GamePlayer();

	}

	@Override
	public void start(Stage primaryStage) {
		
		try {
			primaryStage.setTitle("Casket Catch");
			scenes = new HashMap<String, Pane>();
			HomepageViewController homeViewController = new HomepageViewController(this,backgroundplayer);

			scenes.put("HomepageView", homeViewController.getRoot());

			OptionsViewController optionViewController = new OptionsViewController(this, backgroundplayer); 
			scenes.put("OptionsView", optionViewController.getRoot()); 
			
			LevelViewController levelViewController = new LevelViewController(this, backgroundplayer, gamePlayer);
			scenes.put("LevelView", levelViewController.getRoot());
			
			Pane root = scenes.get("HomepageView");
			Scene scene = new Scene(root, 500, 600);
			URL url = getClass().getResource("/de/basket/style/application.css");
			
			if (url == null) {
				System.err.println("❌ CSS nicht gefunden!");
			} else {
				System.out.println("✅ Gefunden: " + url);
				scene.getStylesheets().add(url.toExternalForm());
			}
			this.primaryStage = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.getIcons().add(new Image(getClass().getResource("/de/basket/images/cherry2.png").toExternalForm()));

			primaryStage.setOnCloseRequest(e -> {
		        backgroundplayer.stopAllMusic();

				Platform.exit();
			});

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchScene(String scene) {
		if (scenes.containsKey(scene)) {
			primaryStage.getScene().setRoot(scenes.get(scene));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() {
		backgroundplayer.stopAllMusic();
		backgroundplayer.threadStopper();
		Platform.exit();
	}

	
	public GamePlayer getRandomUtils() {
		return gamePlayer;
	}

	public void setRandomUtils(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}
	
}
