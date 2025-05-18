package de.basket.scenes.controller.gameController;

import de.basket.application.Main;
import de.basket.business.BackgroundMusikPlayer;
import de.basket.business.BeatListener;
import de.basket.business.BeatListenerObserver;
import de.basket.business.GamePlayer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import de.basket.scenes.controller.HighscoreViewController;
import de.basket.scenes.views.OverlayView;
import de.basket.scenes.views.gameLayout.GameView;

public class GameViewController implements BeatListenerObserver {
	private Main application;
	private Pane root;
	private GameView mainView;
	private Stage overlayStage;

	private GamePlayer gamePlayer;

	private Label scoreLabel;
	private ImageView basketView, fruitView;
	Button backButton, scoreButton, resumeButton, homeButton;
	private Slider volSlider;
	private OverlayView overlay;
	private TranslateTransition translate2, translateTransition;
	private Scene overlayScene;
	private double mouseX;
	public int i, lives = 3;
	double randomToX;
	Minim minim;
	private BeatDetect beat;
	private AudioPlayer aPlayer;
	float timer, savedVolume;
	private HBox hearts;

	HighscoreViewController highscoreViewController;
	BackgroundMusikPlayer musicPlayer = new BackgroundMusikPlayer();
	private boolean isGameRunning = true;

	public GameViewController(Main application, BackgroundMusikPlayer musicPlayer, GamePlayer gamePlayer) {
		this.application = application;
		this.musicPlayer = musicPlayer;
		this.gamePlayer = gamePlayer;

		mainView = new GameView();
		basketView = mainView.basketView;
		scoreButton = mainView.scoreButton;
		fruitView = mainView.fruitView;
		scoreLabel = mainView.scoreLabel;
		hearts = mainView.hearts;

		root = mainView;
		minim = musicPlayer.getMinim();
		beat = musicPlayer.getBeat();
		aPlayer = musicPlayer.getGameMusic();
		BeatListener beatListener = new BeatListener(beat, aPlayer);
		beatListener.addObserver(this);

		initialize();

	}

	public void initialize() {

		gamePlayer.setStartScore();
		musicPlayer.currentTimeProperty().addListener((v, oldValue, newValue) -> {
			setTimer(newValue.floatValue());

			if (newValue.intValue() >= musicPlayer.getCurTrack(gamePlayer.getLevel()).getDuration() / 1000) {
				gameOver();
			}
		});

		double duration = (double) 2 / (gamePlayer.getLevel());

		translate2 = new TranslateTransition(Duration.seconds(duration), fruitView);

		double marginPercentage = 0.05; // 5% margin right and left

		double leftMargin = root.getWidth() * marginPercentage;
		double rightMargin = root.getWidth() * marginPercentage;

		double minRandomX = leftMargin;
		double maxRandomX = root.getWidth() - rightMargin - fruitView.getBoundsInParent().getWidth();
		translate2.setFromY(0);
		translate2.setToY(475);
		randomToX = gamePlayer.getRandomXInRange(minRandomX, maxRandomX);
		fruitView.setTranslateX(randomToX);

		
		translate2.setOnFinished(event -> {
			// Check if the game is still running
			mainView.RandomFruit();
			if (isGameRunning) {

				double finalXBasket = basketView.getTranslateX();
				double finalXFruit = fruitView.getTranslateX();

				boolean collisionResult = gamePlayer.handleCollisionAndGameOver(finalXBasket, finalXFruit);

				if (collisionResult) {
					gamePlayer.addScoredPoints(50); 
					scoreLabel.setText(Integer.toString(gamePlayer.getScore()));
				} else {
					if (lives != 1) {
						removeHeartView();
					} else {
						lives--;
						hearts.getChildren().get(0).setVisible(false);
						gameOver();
					}
				}
				translate2.setFromY(0);

				fruitView.setVisible(false);
				randomToX = gamePlayer.getRandomXInRange(minRandomX, maxRandomX);

				fruitView.setTranslateX(randomToX);
				fruitView.setTranslateY(0);

			}
		});
		translateTransition = new TranslateTransition(Duration.seconds(0), basketView);

		root.setOnMousePressed(e -> {
			mouseX = e.getSceneX() - basketView.getTranslateX();
			translateTransition.setToX(mouseX);
		});

		root.setOnMouseDragged(e -> {

			try {
				double newX = e.getSceneX() - mouseX;

				double maxX = root.getScene().getWidth() - basketView.getBoundsInParent().getWidth();

				newX = Math.max(0, Math.min(newX, maxX));
				translateTransition.setToX(newX);

				translateTransition.play();

				basketView.setTranslateX(newX);

			} catch (NullPointerException exception) {
				exception.printStackTrace(); 
			}
		});

		root.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				stopAnimation();
				openOverlayWindow();
			}
		});

		scoreButton.setOnMouseClicked(e -> {
			stopAnimation();
			openOverlayWindow();
		});
	}

	public void openOverlayWindow() {
		musicPlayer.pauseCurLevelGame();

		if (overlay == null) {
			overlay = new OverlayView();
		}
		resumeButton = overlay.resumeButton;
		homeButton = overlay.homeButton;
		volSlider = overlay.volSlider;
		overlayStage = overlay.overlayStage;
		overlayScene = overlay.overlayScene;

		volSlider.valueProperty().addListener((volumeSaved, oldValue, newValue) -> {
			volSlider.setValue(newValue.floatValue());

			musicPlayer.changeVolumeGamesound((float) volSlider.getValue());

			oldValue = musicPlayer.getSavedVolume();
		});

		overlayScene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				continueAnimation();
				overlay.overlayStage.close();
				musicPlayer.continueMusic();
			}
		});
		resumeButton.setOnMouseClicked(e -> {
			continueAnimation();
			overlayStage.close();
			musicPlayer.continueMusic();
		});

		homeButton.setOnMouseClicked(e -> {
			stopAnimation();
			overlayStage.close();
			application.switchScene("HomepageView");
			musicPlayer.stopCurLevelGame();
		});
		overlayStage.showAndWait();
	}

	public void gameOver() {
		// Stop the game by setting the flag to false
		isGameRunning = false;

		// Stop all animations and transitions
		translate2.stop();
		translateTransition.stop();
		musicPlayer.stopCurLevelGame();


		highscoreViewController = new HighscoreViewController(application, lives, musicPlayer);
		
		Platform.runLater(()-> {
			highscoreViewController.setScoreLabel(gamePlayer.scoredProperty());
			});

		// Set the GameView1 instance to null
		mainView = null;

		// Execute the code to display the HighscoreView in the JavaFX Application
		Platform.runLater(() -> {
			application.primaryStage.getScene().setRoot(highscoreViewController.getRoot());
			application.switchScene("GameView");
		});
	}
	public void removeHeartView() {
		lives--;
		for (int i = 0; i < hearts.getChildren().size(); i++) {
			if (i < lives) {
				hearts.getChildren().get(i).setVisible(true);
			} else {
				hearts.getChildren().get(i).setVisible(false);

			}

		}

	}

	public void stopAnimation() {
		translate2.pause();
	}

	public void continueAnimation() {
		translate2.playFromStart();
	}


	public void setTimer(float timer) {
		this.timer = timer;
	}

	public Pane getRoot() {
		return root;
	}

	@Override
	public void onBeatDetected() {
		fruitView.setVisible(true);
		translate2.play();
	}

}