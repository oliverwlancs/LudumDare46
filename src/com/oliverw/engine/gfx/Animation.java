package com.oliverw.engine.gfx;

import java.util.ArrayList;

public class Animation
{
	private ArrayList<Image> frames;
	private int frameRate; //fps
	private int frameDelay;
	private int currentIndex;
	private int time;
	
	public Animation(ImageTile tileSheet, int numFrames, int frameRate, int scanWidth) {
		frames = new ArrayList<Image>();
		currentIndex = 0;
		time = 0;
		
		this.frameRate = frameRate;
		frameDelay = 60/frameRate;
		
		int tileWidth = tileSheet.getTileWidth();
		int tileHeight = tileSheet.getTileHeight();
		
		int rows = (int)Math.ceil(numFrames / (float)scanWidth);
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < scanWidth; x++) {
				frames.add(tileSheet.getImageTile(x, y));
			}
		}
		
		while(frames.size() != numFrames) {
			frames.remove(numFrames);
		}
	}
	
	public Image getCurrentFrame() {
		return frames.get(currentIndex);
	}
	
	public void update() {
		time++;
		if (time % frameDelay == 0) {
			currentIndex++;
			currentIndex %= frames.size();
		}
	}
}
