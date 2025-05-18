package de.basket.scenes.views;

import javafx.geometry.Insets;
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

public class HomepageView extends BorderPane{
	
	Label titleLabel;
	public Button playButton,optionsButton, exitButton;
	public BackgroundImage myBI;

	
	public HomepageView() {
		
		
		Image backgroundImage = new Image(getClass().getResource("/de/basket/images/ship.gif").toExternalForm(), 0, 700, false,true);
		myBI = new BackgroundImage(backgroundImage,  BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
		
		VBox head = new VBox();
		titleLabel = new Label("Basket Catch");
		titleLabel.getStyleClass().add("maintext");
		
		head.getChildren().addAll(titleLabel);
		head.setAlignment(Pos.CENTER);
		head.setPadding(new Insets(50, 15, 30, 15));
		this.setTop(head);
		
		VBox center = new VBox();
		playButton = new Button("PLAY");
		exitButton = new Button ("EXIT");
		optionsButton = new Button("OPTIONS");
		
		optionsButton.getStyleClass().add("control-button");
		playButton.getStyleClass().add("control-button");
		exitButton.getStyleClass().add("control-button");
		
		center.getChildren().addAll(playButton,optionsButton,exitButton);
		center.setAlignment(Pos.CENTER);
		center.setSpacing(10);
		this.setCenter(center);
		
	}
	
}