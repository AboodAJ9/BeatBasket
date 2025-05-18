package de.basket.scenes.controller;

import de.basket.application.Main;
import de.basket.business.BackgroundMusikPlayer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import de.basket.scenes.views.HomepageView;

public class HomepageViewController {
	private Main application;
	private Pane root;
    private StartViewController startViewController;
    BackgroundImage myBI;
	BackgroundMusikPlayer backgroundplayer;
	Button playButton,exitButton, optionsButton;
	HomepageView homeView; 
	public HomepageViewController(Main application,BackgroundMusikPlayer backgroundplayer) {
		this.application = application;
		this.backgroundplayer = backgroundplayer;
		
		homeView = new HomepageView();
		myBI=homeView.myBI;
		playButton = homeView.playButton;
		exitButton = homeView.exitButton;
		optionsButton = homeView.optionsButton; 
		
		root = homeView;
		root.setId("homepage");
		
        startViewController = new StartViewController(e -> {
            root.getChildren().remove(startViewController.getRoot());
        }, backgroundplayer);
        root.getChildren().add(startViewController.getRoot());

		initialize();
	}
	
	public void initialize() {


		playButton.addEventHandler(ActionEvent.ACTION, e -> {
			application.switchScene("LevelView");
		});
		
		exitButton.addEventHandler(ActionEvent.ACTION,e->{
			backgroundplayer.stopBackgroundMusic();
			Platform.exit();
		});
		
        optionsButton.setOnAction(event -> application.switchScene("OptionsView"));

	}
	public Pane getRoot() {
		return root;
	}
}
