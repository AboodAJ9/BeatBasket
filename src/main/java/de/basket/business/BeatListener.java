package de.basket.business;

import ddf.minim.AudioListener;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BeatListener implements AudioListener {
	private BeatDetect beat;
	private AudioPlayer source;
	private ObservableList<BeatListenerObserver> observers = FXCollections.observableArrayList();

	public BeatListener(BeatDetect beat, AudioPlayer source) {
		this.beat = beat;
		this.source = source;
		this.source.addListener(this);

	}

	public void addObserver(BeatListenerObserver observer) {
		observers.add(observer);
	}

	@Override
	public void samples(float[] sampL) {
	}

	@Override
	public void samples(float[] sampL, float[] sampR) {
		beat.setSensitivity(300);
		beat.detect(source.mix);

		if (beat.isOnset()) {
			triggerObservers();
		}

	}

	private void triggerObservers() {
		for (BeatListenerObserver observer : observers) {
			observer.onBeatDetected();
		}
	}

}



