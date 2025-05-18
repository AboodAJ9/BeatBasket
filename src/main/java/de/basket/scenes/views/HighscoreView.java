package de.basket.scenes.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HighscoreView extends BorderPane {
	public Label scoreTextLabel, scoreLabel, congratsTextLabel;
	public Button nextLvlButton, homepageButton;
	public BackgroundImage highscoreBackground;
	public Image background = new Image(getClass().getResource("/de/basket/images/highscore_background.png").toExternalForm(), 0, 700, false, true);
	public int lives;

	public HighscoreView() {

		highscoreBackground = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		this.setBackground(new Background(highscoreBackground));
		if (background.isError()) {
    		System.err.println("Fehler beim Laden des GIF: " + background.getException());
		}
		VBox center = new VBox();

		congratsTextLabel = new Label("You Won!");
		scoreTextLabel = new Label("Here is the score:");
		scoreLabel = new Label("the real score");

		nextLvlButton = new Button("Next Level");
		homepageButton = new Button("Menu");
		center.getChildren().addAll(congratsTextLabel, scoreTextLabel, scoreLabel, nextLvlButton, homepageButton);
		center.setAlignment(Pos.CENTER);
		center.setSpacing(10);
		congratsTextLabel.getStyleClass().addAll("congratsTextLabel");
		scoreTextLabel.getStyleClass().addAll("scoreTextLabel");
		scoreLabel.getStyleClass().addAll("scoreLabel");
		nextLvlButton.getStyleClass().addAll("nextLvlButton");
		homepageButton.getStyleClass().addAll("homepageButton");
		this.setCenter(center);

	}
}