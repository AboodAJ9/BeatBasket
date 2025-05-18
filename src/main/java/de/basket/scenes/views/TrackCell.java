package de.basket.scenes.views;

import de.basket.business.Track;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TrackCell extends ListCell<Track> {
	HBox root;
	Label title;
	Label bpm;

	public TrackCell() {
		// View-Definition
		getStyleClass().add("list-cell");
		title = new Label();
		bpm = new Label();

		root = new HBox(title, new Region(), bpm);
		HBox.setHgrow(new Region(), Priority.ALWAYS);
		title.getStyleClass().add("label-list");
		bpm.getStyleClass().add("label-list");

		root.setAlignment(Pos.CENTER_LEFT);
		root.setSpacing(10);
		root.setStyle("-fx-padding: 10;");
		
		
		
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	bpm.setStyle("-fx-text-fill: white");
            	title.setStyle("-fx-text-fill: white");
                setStyle("-fx-font-weight: bold; -fx-background-color: purple;");
            }
        });
    }
	

	
	@Override
	protected void updateItem(Track item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			title.setText(item.getTitle());
			bpm.setText("BPM: " + String.valueOf(item.getBpm()));
			this.setGraphic(root);
			setStyle("-fx-font-weight: bold; -fx-background-color: #2ecc71;");
		} else {
			this.setGraphic(null);
		}

	}

}