package de.basket.scenes.controller;

import de.basket.application.Main;
import de.basket.business.BackgroundMusikPlayer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import de.basket.scenes.views.OptionsView;

public class OptionsViewController {
	private Main application;
	private Pane root;

	Button homeButton;
	Label homeMusicLabel, gameMusicLabel;
	Slider homeMusicSlider, gameMusicSlider;

	BackgroundMusikPlayer backgroundplayer;

	public OptionsViewController(Main application, BackgroundMusikPlayer backgroundplayer) {
		this.application = application;
		this.backgroundplayer = backgroundplayer;

		OptionsView optionsView = new OptionsView();

		homeButton = optionsView.homeButton;
		homeMusicSlider = optionsView.homeMusicSlider;

		root = optionsView;
		root.setId("OptionsView");

		initialize();
	}

	public void initialize() {

		homeButton.addEventHandler(ActionEvent.ACTION, e -> {
			application.switchScene("HomepageView");
		});

		homeMusicSlider.valueProperty().addListener((v, oldValue, newValue) -> {

			homeMusicSlider.setValue(newValue.floatValue());

			backgroundplayer.changeVolumeBackground((float) homeMusicSlider.getValue());

		});

	}

	public Pane getRoot() {
		return root;
	}
}