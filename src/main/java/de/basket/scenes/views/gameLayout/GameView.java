package de.basket.scenes.views.gameLayout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class GameView extends BorderPane {

	Image basket = new Image(getClass().getResource("/de/basket/images/basket2.png").toExternalForm());
	Image backgroundImage = new Image(getClass().getResource("/de/basket/images/trees.png").toExternalForm());
	Image heart = new Image(getClass().getResource("/de/basket/images/heart.png").toExternalForm());
	Image pauseImage = new Image(getClass().getResource("/de/basket/images/pause.png").toExternalForm());
	private List<Image> fruits;
	public ImageView basketView, fruitView, pauseImageView;
	public BackgroundImage background;
	public Button backButton, scoreButton;
	public ArrayList<ImageView> heartViews;
	public Label scoreLabel;
	public HBox hearts;
	public int lives = 3;

	public GameView() {
		
		background = new BackgroundImage(backgroundImage,  BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
		this.setBackground(new Background(background));

		pauseImageView = new ImageView(pauseImage);
		pauseImageView.setFitHeight(30);
		pauseImageView.setFitWidth(30);

		this.setWidth(500);
		this.setHeight(600);

		HBox top = new HBox();

		hearts = new HBox();
		heartViews = new ArrayList<>();

		for (int i = 0; i < lives; i++) {
			ImageView heartView = new ImageView(heart);
			heartView.setFitWidth(30);
			heartView.setFitHeight(30);
			heartViews.add(heartView);
			hearts.getChildren().add(heartView);

		}
		hearts.setSpacing(5);
		hearts.setAlignment(Pos.CENTER_LEFT);

		VBox scoreLab = new VBox();
		scoreLabel = new Label("00");
		scoreLab.getChildren().add(scoreLabel);
		scoreLab.setAlignment(Pos.CENTER_LEFT);
		scoreLabel.getStyleClass().add("scoretext");

		VBox buttons = new VBox();
		scoreButton = new Button();
		scoreButton.setGraphic(pauseImageView);
		scoreButton.getStyleClass().add("transparentButton");
		buttons.getChildren().add(scoreButton);
		buttons.setAlignment(Pos.CENTER_RIGHT);

		// Hier wird die Ausrichtung der HBox-Elemente angepasst
		top.getChildren().addAll(hearts,new Pane(), scoreLab,new Pane(), buttons);
		HBox.setHgrow(hearts, Priority.ALWAYS);
		HBox.setHgrow(scoreLab, Priority.ALWAYS);
		HBox.setHgrow(buttons, Priority.ALWAYS);
		
		this.setTop(top);

		VBox img = new VBox();
		basketView = new ImageView(basket);
		basketView.getStyleClass().add("img");

		img.getChildren().add(basketView);
		this.setBottom(img);
		
		 fruits = new ArrayList<>();
	        fruits.add(new Image(getClass().getResource("/de/basket/images/fruits/apple pixel.png").toExternalForm()));
	        fruits.add(new Image(getClass().getResource("/de/basket/images/fruits/cherry2.png").toExternalForm()));
	        fruits.add(new Image(getClass().getResource("/de/basket/images/fruits/strawberry.png").toExternalForm()));
	        fruits.add(new Image(getClass().getResource("/de/basket/images/fruits/banane.png").toExternalForm()));
	        fruits.add(new Image(getClass().getResource("/de/basket/images/fruits/lemon pixel.png").toExternalForm()));
	        fruits.add(new Image(getClass().getResource("/de/basket/images/fruits/watermelone.png").toExternalForm()));
		
	        VBox gameview = new VBox();
	        fruitView = new ImageView();
	        gameview.getChildren().add(fruitView);
	        fruitView.setFitWidth(50);
	        fruitView.setFitHeight(50);
	        RandomFruit();
	        this.setCenter(gameview);
		
	}
	
	public void RandomFruit() {
        Random random = new Random();
        int randomNum = random.nextInt(fruits.size());
        fruitView.setImage(fruits.get(randomNum));
    }
}
