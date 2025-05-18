package de.basket.business;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SongGroup {
	
	private ArrayList<Track> tracklistLevel1 = new ArrayList<Track>();
	private ArrayList<Track> tracklistLevel2 = new ArrayList<Track>();
	private ArrayList<Track> tracklistLevel3 = new ArrayList<Track>();
	private ArrayList<Track> tracklistLevel4 = new ArrayList<Track>();
	private static final int LEVEL1_MIN_BPM = 50;
	private static final int LEVEL1_MAX_BPM = 100;
	private static final int LEVEL2_MIN_BPM = 101;
	private static final int LEVEL2_MAX_BPM = 115;
	private static final int LEVEL3_MIN_BPM = 116;
	private static final int LEVEL3_MAX_BPM = 130;
	private static final int LEVEL4_MIN_BPM = 131;
	private static final int LEVEL4_MAX_BPM = 160;
	File m3uFile = new File("src\\main\\resources\\de\\basket\\songGroups.m3u");

	public SongGroup() {
		distributeTracks();
	}
	private void distributeTracks() {
		String input = " ";
		try (Scanner myReader = new Scanner(m3uFile)) {

			while (myReader.hasNextLine()) {
				input = myReader.nextLine();
				Track curTrack = new Track(input.split(" ")[0]);
				int bpm = Integer.parseInt(input.split(" ")[1]);
				curTrack.setBpm(bpm);

				if (bpm > LEVEL1_MIN_BPM && bpm < LEVEL1_MAX_BPM)
					tracklistLevel1.add(curTrack);
				else if (bpm >= LEVEL2_MIN_BPM && bpm <= LEVEL2_MAX_BPM)
					tracklistLevel2.add(curTrack);
				else if (bpm >= LEVEL3_MIN_BPM && bpm <= LEVEL3_MAX_BPM)
					tracklistLevel3.add(curTrack);
				else if (bpm >= LEVEL4_MIN_BPM && bpm <= LEVEL4_MAX_BPM)
					tracklistLevel4.add(curTrack);
			}
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Track> getTracklistLevel1() {
		return tracklistLevel1;
	}
	public Track getTrackFromFirstLevel(int index) {
		if(!tracklistLevel1.isEmpty())
			return tracklistLevel1.get(index); 
			
		return new Track("src\\main\\resources\\de\\basket\\songs\\Hause.mp3");
	}
	public ArrayList<Track> getTracklistLevel2() {
		return tracklistLevel2;
		
	}
	public Track getTrackFromSecondLevel(int index) {
		if(!tracklistLevel2.isEmpty()) 
			return tracklistLevel2.get(index); 
		
		return new Track("src\\main\\resources\\de\\basket\\songs\\Clown(Chosic.com).mp3"); 

	} 
	public ArrayList<Track> getTracklistLevel3() {
		return tracklistLevel3;
	}
	public Track getTrackFromThirdLevel(int index) {
		if(!tracklistLevel2.isEmpty()) 
			return tracklistLevel3.get(index); 
		return new Track("src\\main\\resources\\de\\basket\\songs\\Sakura-Girl-Daisy.mp3"); 

	}
	public ArrayList<Track> getTracklistLevel4() {
		return tracklistLevel4;
	}
	public Track getTrackFromForthLevel(int index) {
		if(!tracklistLevel2.isEmpty()) 
			return tracklistLevel4.get(index); 
		return new Track("src\\main\\resources\\de\\basket\\songs\\Hitman.mp3");
	}
}