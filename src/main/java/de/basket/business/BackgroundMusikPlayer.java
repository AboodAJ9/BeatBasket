package de.basket.business;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import ddf.minim.*;
import ddf.minim.analysis.*;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class BackgroundMusikPlayer {
	private Minim minim;
	public AudioPlayer backgroundMusic, startGameSound;
	private SimpleBooleanProperty startSongIsPlaying;
	private CountDownLatch startLatch;
	private SongGroup sGroup;
	private boolean isPlaying;
	private float savedVolume;
	private SimpleIntegerProperty currentTime;
	private Track gameTrack;
	private BeatDetect beat;
	private List<SimpleObjectProperty<Track>> curTracks;
	Thread timeThread;
	AudioPlayer gameMusic;

	public BackgroundMusikPlayer() {

		this.minim = new Minim(new MinimHelper());
		backgroundMusic = minim.loadFile("src\\main\\resources\\de\\basket\\songs\\marioSound.mp3");
        startGameSound = minim.loadFile("src\\main\\resources\\de\\basket\\songs\\startGame.mp3");
		beat = new BeatDetect();
		currentTime = new SimpleIntegerProperty(0);
		startSongIsPlaying = new SimpleBooleanProperty(false);
		startLatch = new CountDownLatch(30);
		sGroup = new SongGroup();
		curTracks = new ArrayList<>();
		addcurTracks();

	}
	public void addcurTracks() {
		curTracks.add(0, new SimpleObjectProperty<>(sGroup.getTracklistLevel1().get(0)));
		curTracks.add(1, new SimpleObjectProperty<>(sGroup.getTracklistLevel2().get(0)));
		curTracks.add(2, new SimpleObjectProperty<>(sGroup.getTracklistLevel3().get(0)));
		curTracks.add(3, new SimpleObjectProperty<>(sGroup.getTracklistLevel4().get(0)));
	}

	public void setCurTrack(Track newTrack, int index) {
		if (index <= curTracks.size()) {
			curTracks.get(index - 1).setValue(newTrack);
		}
	}

	public Track getCurTrack(int index) {
		return curTracks.get(index - 1).getValue();
	}

	public Track getGameTrack() {
		return gameTrack;
	}

	public void setGameTrack(Track gameTrack) {
		this.gameTrack = gameTrack;
	}

	public void playCurLevelGame(int level) {
		gameMusic = minim.loadFile(curTracks.get(level - 1).get().getPath());
		setCurTrack(curTracks.get(level - 1).get(), level);
		setGameTrack(getCurTrack(level));
		setIsPlaying(true);
		gameMusic.setGain(savedVolume);
		gameMusic.play();

		if (isPlaying) {
			currentTime.setValue(0);
			if (timeThread != null && timeThread.isAlive()) {
				timeThread.interrupt();
			}

			timeThread = new Thread(() -> {

				while (isPlaying) {

					currentTime.setValue(currentTime.getValue() + 1);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						return;
					}
				}
			});

			timeThread.start();
		}
	}

	public void threadStopper() {
		if (timeThread != null)
			timeThread.interrupt();
	}

	public SimpleIntegerProperty currentTimeProperty() {
		return currentTime;
	}

	public BeatDetect getBeat() {
		return beat;
	}

	public void setBeat(BeatDetect beat) {
		this.beat = beat;
	}

	public Minim getMinim() {
		return minim;
	}

	public void setMinim(Minim minim) {
		this.minim = minim;
	}

	public AudioPlayer getGameMusic() {
		return gameMusic;
	}

	public void setGameMusic(AudioPlayer gameMusic) {
		this.gameMusic = gameMusic;
	}

	public void continueMusic() {
		if (timeThread != null && timeThread.isAlive()) {
			// Thread is already running, no need to start it again
			return;
		}
		timeThread = new Thread(() -> {
			while (isPlaying) {
				currentTime.setValue(currentTime.getValue() + 1);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		});

		timeThread.start();

		if (isPlaying) {
			// setIsPlaying(true);
			gameMusic.play();
		}
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public void stopCurLevelGame() {
		setIsPlaying(false);
		gameMusic.close();
		playBackgroundMusic();
	}

	public void stopAllMusic() {
		if (gameMusic != null)
			gameMusic.close();
		if (backgroundMusic != null)
			backgroundMusic.close();

	}

	public void pauseCurLevelGame() {
		if (timeThread != null && timeThread.isAlive()) {
			timeThread.interrupt();
		}

		if (gameMusic != null) {
			// setIsPlaying(false);
			gameMusic.pause();
		}
	}

	public void playStartGameSound() {
		new Thread(() -> {
			startGameSound.rewind();
			startGameSound.play();
			startSongIsPlaying.set(true);
			startLatch.countDown(); // Notify that the sound has started
		}).start();
	}

	public void stopStartGameSound() {
		startGameSound.pause();
		startSongIsPlaying.set(false);
		backgroundMusic.loop();
	}

	public void playBackgroundMusic() {
		if (!startSongIsPlaying.getValue()) {
			backgroundMusic.loop();
		}
	}

	public void stopBackgroundMusic() {
		backgroundMusic.pause();
		backgroundMusic.rewind();
	}

	public SimpleBooleanProperty startSongIsPlayingProperty() {
		return startSongIsPlaying;
	}

	public void changeVolumeBackground(float value) {
		backgroundMusic.setGain((float) (10 * Math.log10(value)));
	}

	public void changeVolumeGamesound(float value) {
		gameMusic.setGain((float) (10 * Math.log10(value)));
		savedVolume = gameMusic.getGain();
	}

	public float getSavedVolume() {
		return savedVolume;
	}

}
