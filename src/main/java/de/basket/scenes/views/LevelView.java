package de.basket.scenes.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LevelView extends BorderPane {
    public Button homeButton;
    LevelContainer levelContainer; 
    Image backarrow = new Image(getClass().getResource("/de/basket/images/backarrow.png").toExternalForm());
    ImageView backarrowView;
    public LevelView() {
        VBox header = createHeader();
        this.setTop(header);

        VBox c = createLevelContainers();
        this.setCenter(c);
        this.getStyleClass().add("levelviewbackground");
    }

    private VBox createHeader() {
    	
    	backarrowView = new ImageView(backarrow);
    	backarrowView.setFitWidth(25);
    	backarrowView.setFitHeight(25);
    	
        VBox header = new VBox();

        HBox home = new HBox();
        homeButton = new Button();
        homeButton.setGraphic(backarrowView);
        homeButton.getStyleClass().add("transparentButton");
        home.getChildren().addAll(homeButton);
        home.setAlignment(Pos.BASELINE_LEFT);

        HBox title = new HBox();
        Label titleLabel = new Label("Select a Level");
        titleLabel.getStyleClass().add("maintext");
        title.getChildren().addAll(titleLabel);
        title.setAlignment(Pos.CENTER);

        
		HBox.setHgrow(home, Priority.ALWAYS);
		HBox.setHgrow(title, Priority.ALWAYS);

		
        header.getChildren().addAll(home,title);
        return header;
    }

    private VBox createLevelContainers() {
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        center.setSpacing(25);

        for (int i = 1; i <= 4; i++) {
            levelContainer = new LevelContainer(i);
            center.getChildren().add(levelContainer);
        }

        return center;
    }
    
}
