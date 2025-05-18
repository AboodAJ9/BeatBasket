package de.basket.scenes.controller;

import java.util.ArrayList;
import java.util.List;
import de.basket.application.Main;
import de.basket.business.BackgroundMusikPlayer;
import de.basket.business.GamePlayer;
import de.basket.business.SongGroup;
import de.basket.business.Track;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import de.basket.scenes.views.TrackCell;
import de.basket.scenes.controller.gameController.GameViewController;
import de.basket.scenes.views.LevelContainer;
import de.basket.scenes.views.LevelView;

public class LevelViewController {
	private Main application;
	private Pane root;
	private Button homeButton;
	private VBox center;
	private SongGroup sg;
	private BackgroundMusikPlayer musicPlayer;
	private List<Track> tracklst1, tracklst2, tracklst3, tracklst4;
	ListView<Track> trackListView;
	private LevelContainer level1Container, level2Container, level3Container, level4Container;
	private GamePlayer randomUtils;
	private GameViewController gameViewController;
	private List<StackPane> overlayList = new ArrayList<>();

	public LevelViewController(Main application, BackgroundMusikPlayer musicPlayer, GamePlayer randomUtils) {
		this.application = application;
		this.musicPlayer = musicPlayer;
		this.randomUtils = randomUtils;

		LevelView mainView = new LevelView();
		homeButton = mainView.homeButton;
		center = (VBox) mainView.getCenter();
		level1Container = (LevelContainer) center.getChildren().get(0);
		level2Container = (LevelContainer) center.getChildren().get(1);
		level3Container = (LevelContainer) center.getChildren().get(2);
		level4Container = (LevelContainer) center.getChildren().get(3);
		sg = new SongGroup();
		for (int i = 1; i <= 4; i++) {
			setSongTitle(i);
		}

		tracklst1 = sg.getTracklistLevel1();
		tracklst2 = sg.getTracklistLevel2();
		tracklst3 = sg.getTracklistLevel3();
		tracklst4 = sg.getTracklistLevel4();

		root = mainView;
		initialize();
	}

	public void initialize() {
		// Add event handlers for each LevelContainer
		Platform.runLater(() -> {
			for (int i = 0; i < randomUtils.getSucceed().size(); i++) {
				if (randomUtils.getSucceed().get(i).booleanValue() == false) {
					addOverlap(i + 1);
				}
			}
		});
		randomUtils.succeedProperty().addListener((observable, oldValue, newValue) -> {
			if (randomUtils.getLevel() < newValue.size() && newValue.get(randomUtils.getLevel())) {
				removeOverlap(randomUtils.getLevel()+1);
			}
		});

		setPlayGameHandler(level1Container, 1);
		setPlayGameHandler(level2Container, 2);
		setPlayGameHandler(level3Container, 3);
		setPlayGameHandler(level4Container, 4);

		homeButton.addEventHandler(ActionEvent.ACTION, e -> {
			application.switchScene("HomepageView");
		});
		level1Container.getArrowIcon().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> showTrackList(1));
		level2Container.getArrowIcon().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> showTrackList(2));
		level3Container.getArrowIcon().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> showTrackList(3));
		level4Container.getArrowIcon().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> showTrackList(4));
	}

	private void removeOverlap(int level) {
		if (level < 2 || level - 1 > overlayList.size()) {
			throw new IndexOutOfBoundsException("Invalid level: " + level);
		}
		StackPane overlayToRemove = overlayList.get(level - 2);
		switch (level) {
		case 2:
			level2Container.getChildren().remove(overlayToRemove);
			break;
		case 3:
			level3Container.getChildren().remove(overlayToRemove);
			break;
		case 4:
			level4Container.getChildren().remove(overlayToRemove);
			break;
		default:
			throw new IndexOutOfBoundsException("Invalid level: " + level);
		}
	}
	private void addOverlap(int level) {
		StackPane lock_overlay = new StackPane();
		ImageView overlapImageView = new ImageView(new Image(getClass().getResource("/de/basket/images/overlay.png").toExternalForm()));
		overlapImageView.setFitHeight(63);
		overlapImageView.setFitWidth(475);
		overlapImageView.setStyle("-fx-background-radius: 20;");
		StackPane.setAlignment(overlapImageView, Pos.CENTER);
		lock_overlay.setStyle("-fx-background-radius: 20;");

		lock_overlay.getChildren().addAll(overlapImageView);
		overlayList.add(lock_overlay);

		switch (level) {
		case 2:
			level2Container.setSpacing(-55);
			level2Container.getChildren().add(lock_overlay);
			break;
		case 3:
			level3Container.setSpacing(-55);
			level3Container.getChildren().add(lock_overlay);
			break;
		case 4:
			level4Container.setSpacing(-55);
			level4Container.getChildren().add(lock_overlay);
			break;
		default:
			throw new IndexOutOfBoundsException("Invalid level: " + level);

		}
	}

	private void setPlayGameHandler(LevelContainer container, int level) {
		container.getPlayButton().addEventHandler(ActionEvent.ACTION, e -> {
			randomUtils.setLevel(level);
			musicPlayer.stopBackgroundMusic();
			musicPlayer.playCurLevelGame(level);

			gameViewController = new GameViewController(application, musicPlayer, randomUtils);
			application.primaryStage.getScene().setRoot(gameViewController.getRoot());
			application.switchScene("GameView");
		});

	}

	private void showTrackList(int levelNumber) {
		Dialog<Void> dialog = new Dialog<>();
		dialog.setTitle("Track List");
		dialog.getDialogPane().setMinWidth(400);
		dialog.getDialogPane().setMinHeight(600);
		ListView<Track> trackListView = createStyledTrackListView(levelNumber);
		dialog.getDialogPane().getStyleClass().add("track-list-dialog");
		dialog.getDialogPane().setContent(trackListView);
		ButtonType closeButton = new ButtonType("Close");
		dialog.getDialogPane().getButtonTypes().add(closeButton);

		dialog.getDialogPane().setStyle("-fx-background-color: #336699;");

		String baseStyle = "-fx-background-color: #953F3F; -fx-text-fill: white; -fx-font-size: 15;-fx-font-weight: bold; -fx-background-radius: 5;";

		Node closeButtonNode = dialog.getDialogPane().lookupButton(closeButton);
		closeButtonNode.setStyle(baseStyle);
		closeButtonNode.setOnMouseEntered(e -> closeButtonNode.setStyle(baseStyle + " -fx-background-color: red;"));
		closeButtonNode.setOnMouseExited(e -> closeButtonNode.setStyle(baseStyle));

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == closeButton) {
				dialog.close();
			}
			return null;
		});

		// reagieren auf selektiertem song
		trackListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Track>() {
			@Override
			public void changed(ObservableValue<? extends Track> observable, Track oldTrack, Track newTrack) {
				try {
					// musicPlayer.setCurTrack_firstLevel(newTrack);
					musicPlayer.setCurTrack(newTrack, levelNumber);
					setUpdatedTitles(newTrack, levelNumber);
				} catch (NullPointerException e) {
				}
			}
		});
		dialog.showAndWait();
	}

	private void setUpdatedTitles(Track newTrack, int levelNumber) {
		switch (levelNumber) {
		case 1:
			level1Container.setSongTitleLabel(musicPlayer.getCurTrack(1).getTitle());
			break;
		case 2:
			level2Container.setSongTitleLabel(musicPlayer.getCurTrack(2).getTitle());
			break;
		case 3:
			level3Container.setSongTitleLabel(musicPlayer.getCurTrack(3).getTitle());
			break;
		case 4:
			level4Container.setSongTitleLabel(musicPlayer.getCurTrack(4).getTitle());
		}
	}

	private ListView<Track> createStyledTrackListView(int levelNumber) {
		ListView<Track> trackListView = new ListView<>();
		ArrayList<Track> tracklist = (ArrayList<Track>) getTracklistByLevelNumber(levelNumber);

		// Populate the trackListView with track titles and BPM
		for (Track track : tracklist) {
			trackListView.getItems().add(track);
		}
		trackListView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {
			@Override
			public ListCell<Track> call(ListView<Track> param) {
				return new TrackCell();
			}
		});
		trackListView.setStyle("-fx-padding: 10;");

		// Apply styling to the ListView here
		trackListView.getStyleClass().add("list-cell");
		return trackListView;
	}

	private List<Track> getTracklistByLevelNumber(int levelNumber) {
		switch (levelNumber) {
		case 1:
			return tracklst1;
		case 2:
			return tracklst2;
		case 3:
			return tracklst3;
		case 4:
			return tracklst4;
		default:
			return new ArrayList<>();
		}
	}

	private void setSongTitle(int levelNumber) {
		switch (levelNumber) {
		case 1:
			level1Container.setSongTitleLabel(musicPlayer.getCurTrack(levelNumber).getTitle());
		case 2:
			level2Container.setSongTitleLabel(musicPlayer.getCurTrack(levelNumber).getTitle());
		case 3:
			level3Container.setSongTitleLabel(musicPlayer.getCurTrack(levelNumber).getTitle());
		case 4:
			level4Container.setSongTitleLabel(musicPlayer.getCurTrack(levelNumber).getTitle());
		}
	}

	

	public Pane getRoot() {
		return root;
	}
}