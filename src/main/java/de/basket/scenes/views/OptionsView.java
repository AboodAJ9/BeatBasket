package de.basket.scenes.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class OptionsView extends BorderPane{
	
	Label titleLabel,homeMusicLabel,gameMusicLabel;
	public Button homeButton;
	public Slider homeMusicSlider;
	private Label howToPlay,howToPlaylabel;

	
	public OptionsView() {
		
		this.getStyleClass().add("optionView");
		VBox head = new VBox();
		titleLabel = new Label("Options");
		titleLabel.getStyleClass().add("maintext");
		
		head.getChildren().addAll(titleLabel);
		head.setAlignment(Pos.CENTER);
		head.setPadding(new Insets(50, 15, 0, 15));
		this.setTop(head);
		
		
		VBox center = new VBox();
		homeMusicLabel = new Label("Background Music");
		homeMusicLabel.getStyleClass().add("subtext");
		homeMusicLabel.getStyleClass().add("");
		homeMusicSlider = new Slider (0,1,0.5);
		howToPlaylabel = new Label("How to Play");
		howToPlaylabel.getStyleClass().add("subtext");
		howToPlay = new Label("You have to click in the Window and you have to catch the Fruits.The game ends if you lose your hearts or if you can survive till the song ends! \n Good Luck!");
		howToPlay.setWrapText(true);
		howToPlay.setTextAlignment(TextAlignment.CENTER);
		
		center.getChildren().addAll(homeMusicLabel,homeMusicSlider,howToPlaylabel,howToPlay);
		center.setAlignment(Pos.CENTER);
		center.setSpacing(40);
		center.setPadding(new Insets(0, 120, 0, 120));
		this.setCenter(center);
		
		
		VBox bottom = new VBox();
		homeButton = new Button ("HOME");
		homeButton.getStyleClass().add("control-button");
		
		bottom.getChildren().addAll(homeButton);
		bottom.setPadding(new Insets(0, 0, 50, 0));
		bottom.setAlignment(Pos.CENTER);
		this.setBottom(bottom);
		
	}
	
}