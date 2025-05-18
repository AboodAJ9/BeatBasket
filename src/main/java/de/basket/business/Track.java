package de.basket.business;

import java.io.IOException;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Track {
	private String title;
	private String fileName;
	private int duration;
	private int bpm; 
	Mp3File mp3file = null;
	ID3v2 meta;
	byte[] albumImage;



	public Track(String path) {
		try {
			mp3file = new Mp3File(path);
			meta = mp3file.getId3v2Tag();

			this.fileName = path;
			this.title = meta.getTitle();
			this.albumImage = meta.getAlbumImage();
			this.duration = (int) mp3file.getLengthInSeconds() * 1000;
			bpm = 0; 

		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedTagException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

	public Track(String title, String fileName) {
		super();
		this.title = title;
		this.fileName = fileName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public byte[] getAlbumImage() {

		return albumImage;
	}

	public void setAlbumImage(byte[] albumImage) throws InvalidDataException {
		this.albumImage = albumImage;
	}

	public String getTitle() {
		return title!= null? title: " ";
	}

	public String getFileName() {
		return fileName;
	}

	public String getPath() {
		return fileName;
	}
	
	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

}