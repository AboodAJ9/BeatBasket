package de.basket.business;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GamePlayer {
	int level;
	private ListProperty<Boolean> succeed;
	private SimpleIntegerProperty score;

	public GamePlayer() {
		succeed = new SimpleListProperty<>(FXCollections.observableArrayList(true, false, false, false));
		level = 0;
		score = new SimpleIntegerProperty();  
		score.setValue(0);
	}

	public boolean handleCollisionAndGameOver(double finalXBasket, double finalXFruit) {
	    return finalXBasket + 90 >= finalXFruit && finalXBasket - 30 < finalXFruit;
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public int scoredProperty() {
		return score.getValue();

	}

	public void setScore(SimpleIntegerProperty score) {
		this.score = score;
	}

	public void setStartScore () {
		this.score.set(0); 
	}
	public void addScoredPoints(int points) {
		this.score.set(score.get()+points);

	}
	public int getScore() {
		return score.get();
	}

	public double getRandomXInRange(double min, double max) {
		return min + Math.random() * (max - min);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ObservableList<Boolean> getSucceed() {
		return succeed.get();
	}

	public ListProperty<Boolean> succeedProperty() {
		return succeed;
	}

	public void setSucceed(ObservableList<Boolean> succeed) {
		this.succeed.set(FXCollections.observableArrayList(succeed));
	}

	public void setLevelSucceeded(int level) {
		if (level >= 0 && level <= succeed.size()) {

			succeed.set(level - 1, true);
		} else {
			throw new IllegalArgumentException("Invalid level index: " + level);
		}
	}
}