package de.basket.scenes.views; 

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;



public class StartView extends Pane {
	private static final double VIEW_WIDTH = 500; 
    private static final double VIEW_HEIGHT = 600;
	List<ImageView> fruits;
	Image backgroundImage;    

    public StartView() {
    	setPrefSize(VIEW_WIDTH, VIEW_HEIGHT);
        backgroundImage = new Image(getClass().getResource("/de/basket/images/backg7.png").toExternalForm());
		fruits = new ArrayList<>();
        if (backgroundImage.isError()) 
            System.out.println("Error landing");

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(VIEW_WIDTH, VIEW_HEIGHT, false, false, false, false));
        setBackground(new Background(background));
        initializeFruits();
    }
	private List<ImageView> initializeFruits() {

		for (int i = 0; i < 3; i++) {
			fruits.add(createFruit("apple pixel.png", 50, 50, 2.0));
		}

		for (int i = 0; i < 4; i++) {
			fruits.add(createFruit("lemon pixel.png", 60, 60, 1.5));
		}

		for (int i = 0; i < 5; i++) {
			fruits.add(createFruit("cherry2.png", 60, 60, 2.5));
		}

		return fruits;
	}

	private ImageView createFruit(String imageName, double width, double height, double speed) {
		Image image = new Image(getClass().getResource("/de/basket/images/fruits/").toExternalForm() + imageName);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);

		Random random = new Random();
		double x = random.nextDouble() * (500 - width);
		double y = random.nextDouble() * (600 - height);
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);

		imageView.setUserData(speed);
		return imageView;
	}

	public List<ImageView> getFruits() {
		return fruits;
	}
}


