package de.basket.scenes.controller; 

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import de.basket.scenes.views.StartView;
import java.util.List;
import de.basket.business.BackgroundMusikPlayer;

public class StartViewController {
	private StartView startView;
	private Pane root;
	private EventHandler<ActionEvent> onAnimationFinished;
	private BackgroundMusikPlayer backgroundplayer;

	public StartViewController(EventHandler<ActionEvent> onAnimationFinished, BackgroundMusikPlayer backgroundplayer) {
		this.onAnimationFinished = onAnimationFinished;
		startView = new StartView();
		this.backgroundplayer = backgroundplayer;
		root = new Pane(startView);
		initialize();
	}

	private void initialize() {
		List<ImageView> fruits = startView.getFruits();

		// The start song is playing, proceed with the animation
		Timeline timeline = new Timeline();
		for (ImageView fruit : fruits) {
			double delay = Math.random() * 0.3;
			timeline.getKeyFrames().addAll(
					new KeyFrame(Duration.seconds(delay), new KeyValue(fruit.translateYProperty(), -100)),
					new KeyFrame(Duration.seconds(delay + (double) fruit.getUserData()),
							new KeyValue(fruit.translateYProperty(), 550)));
		}

		timeline.setCycleCount(1);
		timeline.setOnFinished(e -> {
			// Stop the start-sound when the animation finishes
			backgroundplayer.stopStartGameSound();
			onAnimationFinished.handle(e);
		});

		startView.getChildren().addAll(fruits);
		timeline.play();

		backgroundplayer.playStartGameSound();
	}

	public Pane getRoot() {
		return root;
	}
}