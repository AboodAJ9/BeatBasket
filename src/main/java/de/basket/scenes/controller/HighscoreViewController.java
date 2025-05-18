package de.basket.scenes.controller;

import de.basket.application.Main;
import de.basket.business.BackgroundMusikPlayer;
import de.basket.business.GamePlayer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import de.basket.scenes.controller.gameController.GameViewController;
import de.basket.scenes.views.HighscoreView;

public class HighscoreViewController {
    private Main application;
    private Pane root;
    private int lives, level;
    private GameViewController gameViewController;
    Label scoreTextLabel, scoreLabel,resultTextLabel;
    Button nextLvlButton, homepageButton;
    HighscoreView mainView;
    GamePlayer gamePlayer; 
    BackgroundMusikPlayer musicPlayer; 
    



    public HighscoreViewController(Main application,int lives,BackgroundMusikPlayer musicPlayer) {
        this.application = application;
        this.musicPlayer = musicPlayer;
        this.lives=lives;
        gamePlayer= application.getRandomUtils(); 
        level = gamePlayer.getLevel(); 
        mainView = new HighscoreView();
        resultTextLabel=mainView.congratsTextLabel;
        scoreTextLabel = mainView.scoreTextLabel;
        scoreLabel = mainView.scoreLabel;
        homepageButton = mainView.homepageButton;
        nextLvlButton = mainView.nextLvlButton;
        root = mainView;

        initialize();

    }

    public void initialize() {

        if(lives==0) {
            nextLvlButton.setVisible(false);
            resultTextLabel.setText("You Lost!");

        }else if(gamePlayer.getLevel()==4){
        	nextLvlButton.setVisible(false);
        	resultTextLabel.setText("Congratiulation, you won all levels!");
        	
        	
        }
        else {
        	if(gamePlayer.getLevel()<4) gamePlayer.setLevelSucceeded(level+1);
             nextLvlButton.setVisible(true);
             nextLvlButton.addEventFilter(ActionEvent.ACTION, e -> {
            	 startNextLevel();
             });
        }

        homepageButton.addEventFilter(ActionEvent.ACTION, e -> {
            application.switchScene("HomepageView");

        });

    }

    public void startNextLevel() {
		int currentLevel = gamePlayer.getLevel();
		int nextLevel = currentLevel + 1;

		// Überprüfen, ob das nächste Level existiert
		if (nextLevel <= 4) {
			gamePlayer.setLevel(nextLevel);
			gamePlayer.setStartScore();
			musicPlayer.stopBackgroundMusic();
			musicPlayer.playCurLevelGame(nextLevel);

			gameViewController = new GameViewController(application, musicPlayer, gamePlayer);
			application.primaryStage.getScene().setRoot(gameViewController.getRoot());
			application.switchScene("GameView");
		} 
	}
    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }


    public Pane getRoot() {
        return root;
    }

    public void setScoreLabel(int string) {
        this.scoreLabel.setText(Integer.toString(string));

    }

     public void setLives(int lives) {
            this.lives = lives;
        }
}