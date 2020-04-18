package com.oliverw.engine.gfx;

import java.util.ArrayList;

public class RenderLayer 
{
	private int zIndex;
	
	private ArrayList<Image> images;
	
	public RenderLayer(int zIndex) {
		this.zIndex = zIndex;
		
		images = new ArrayList<Image>();
	}

	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	public void addImage(Image image) {
		images.add(image);
	}
	
	public ArrayList<Image> getImages() {
		return images;
	}
}
