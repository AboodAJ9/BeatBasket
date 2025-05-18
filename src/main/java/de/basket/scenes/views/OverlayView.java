package de.basket.scenes.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OverlayView extends BorderPane {
	public Stage overlayStage;
	public Button resumeButton, homeButton;
	public Slider volSlider;
	public Label vollabel;
	public Scene overlayScene;

	public OverlayView() {

		overlayStage = new Stage();
		overlayStage.initModality(Modality.APPLICATION_MODAL);
		overlayStage.initStyle(StageStyle.TRANSPARENT);


		// Hier können Sie den Inhalt des Overlay-Fensters definieren
		vollabel = new Label("Game Volume");
		vollabel.getStyleClass().add("gametext");

		resumeButton = new Button("Resume");
		resumeButton.getStyleClass().add("play-button");

		homeButton = new Button("Menu");
		homeButton.getStyleClass().add("play-button");

		volSlider = new Slider(0, 1, 0.5);
		volSlider.getStyleClass().add("volSlider");

		HBox sliderBox = new HBox(volSlider);
		sliderBox.setAlignment(Pos.CENTER);
		sliderBox.getStyleClass().add("slider-box");

		VBox buttons = new VBox(vollabel, sliderBox, resumeButton, homeButton);
		buttons.setSpacing(15);
		buttons.setAlignment(Pos.CENTER);

		StackPane overlayRoot = new StackPane();
		overlayRoot.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
		overlayRoot.getChildren().addAll(buttons);

		buttons.prefWidthProperty().bind(overlayRoot.widthProperty());
		buttons.prefHeightProperty().bind(overlayRoot.heightProperty());

		overlayScene = new Scene(overlayRoot, 300, 400);
		overlayStage.setScene(overlayScene);
		overlayScene.getStylesheets().add(getClass().getResource("/de/basket/style/test.css").toExternalForm());
	
	
	}
}
