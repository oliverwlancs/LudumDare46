package com.oliverw.engine;

import java.awt.image.DataBufferInt;

import com.oliverw.engine.gfx.Font;
import com.oliverw.engine.gfx.Image;
import com.oliverw.engine.gfx.ImageTile;

public class Renderer {

	private int pW, pH;
	private int[] p;
	
	private Font font = Font.STANDARD;
	
	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
		}
	}
	
	public void setPixel(int x, int y, int value) {
		if ((x < 0 || x >= pW || y < 0 || y >= pH) || ((value >> 24) & 0xff) == 0) {
			return;
		}
		
		p[x + y * pW] = value;
	}
	
	public void drawText(String text, int offX, int offY, int colour) {
		text = text.toUpperCase();
		int offset = 0;
		
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			
			Image fontImage = font.getFontImage();
			
			for (int y = 0; y < fontImage.getHeight(); y++) {
				for (int x = 0; x < font.getWidths()[unicode]; x++) {
					if (fontImage.getP()[(x + font.getOffsets()[unicode]) + y * fontImage.getWidth()] == 0xffffffff) {
						setPixel(x + offset + offX, y + offY, colour);
					}
				}
			}
			
			offset += font.getWidths()[unicode];
		}
	}
	
	public void drawImage(Image image, int offX, int offY) {
		if (offX < -image.getWidth() || offX >= pW) {
			return;
		}
		
		if (offY < -image.getHeight() || offY >= pH) {
			return;
		}
		
		int newX = 0, newY = 0;
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();
		
		if (offX < 0) {
			newX -= offX;
		}
		
		if (newWidth + offX > pW) {
			newWidth -= newWidth + offX - pW;
		}
		
		if (offY < 0) {
			newY -= offY;
		}
		
		if (newHeight + offY > pH) {
			newHeight -= newHeight + offY - pH;
		}
		
		for(int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[x + y * image.getWidth()]);
			}
		}
	}
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
		if (offX < -image.getTileWidth() || offX >= pW) {
			return;
		}
		
		if (offY < -image.getTileHeight() || offY >= pH) {
			return;
		}
		
		int newX = 0, newY = 0;
		int newWidth = image.getTileWidth();
		int newHeight = image.getTileHeight();
		
		if (offX < 0) {
			newX -= offX;
		}
		
		if (newWidth + offX > pW) {
			newWidth -= newWidth + offX - pW;
		}
		
		if (offY < 0) {
			newY -= offY;
		}
		
		if (newHeight + offY > pH) {
			newHeight -= newHeight + offY - pH;
		}
		
		for(int y = newY; y < newHeight; y++) {
			for (int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
			}
		}
	}
	
	public void drawRect(int offX, int offY, int width, int height, int borderColour) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					setPixel(x + offX, y + offY, borderColour);
				}
			}
		}
	}
	
	public void drawRect(int offX, int offY, int width, int height, int borderColour, int fillColour) {
		drawRect(offX, offY, width, height, borderColour);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!(x == 0 || y == 0 || x == width - 1 || y == height - 1)) {
					setPixel(x + offX, y + offY, fillColour);
				}
			}
		}
	}
}
