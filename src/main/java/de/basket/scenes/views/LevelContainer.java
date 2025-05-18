package de.basket.scenes.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelContainer extends VBox {
	private int levelNumber;
	Button playButton;
	Label songTitleLabel;
	ImageView arrowIcon;

	public LevelContainer(int levelNumber) {
		this.levelNumber = levelNumber;
		this.songTitleLabel = new Label();

		initialize();
	}

	private void initialize() {
		setSpacing(10);
		setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: #050f16");
		setPrefWidth(100); // Adjust the width as needed
		setPrefHeight(75);
		Label levelLabel = new Label("Level " + levelNumber);
		levelLabel.getStyleClass().add("levelText");

		songTitleLabel.getStyleClass().add("song-title-label");

		arrowIcon = new ImageView(new Image(getClass().getResource("/de/basket/images/polygon 1.png").toExternalForm()));
		arrowIcon.getStyleClass().add("iconArrow");
		arrowIcon.setFitWidth(20);
		arrowIcon.setFitHeight(15);
		
        ImageView arrowIcon2 = new ImageView(new Image(getClass().getResource("/de/basket/images/polygon 2.png").toExternalForm()));
        arrowIcon2.getStyleClass().add("iconArrow");
        arrowIcon2.setFitWidth(20);
        arrowIcon2.setFitHeight(15);
        
        
        //hovering
        arrowIcon.setOnMouseEntered(e -> {
            arrowIcon.setImage(arrowIcon2.getImage());
        });

        arrowIcon.setOnMouseExited(e -> {
            arrowIcon.setImage(new Image(getClass().getResource("/de/basket/images/polygon 1.png").toExternalForm()));
        });
        
        
		playButton = new Button("Play");
		playButton.getStyleClass().add("play-button");

		HBox content = new HBox(levelLabel, songTitleLabel, arrowIcon, playButton);
		content.setSpacing(20);
		content.setAlignment(Pos.CENTER);

		getChildren().add(content);

	}

	public Label getSongTitleLabel() {
		return songTitleLabel;
	}

	public void setSongTitleLabel(String newTitle) {
		this.songTitleLabel.setText(newTitle);
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public ImageView getArrowIcon() {
		return arrowIcon;
	}

	public void setArrowIcon(ImageView arrowIcon) {
		this.arrowIcon = arrowIcon;
	}

	public Button getPlayButton() {
		return playButton;
	}

	public void setPlayButton(Button playButton) {
		this.playButton = playButton;
	}

}