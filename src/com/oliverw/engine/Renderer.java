package com.oliverw.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.oliverw.engine.gfx.Font;
import com.oliverw.engine.gfx.Image;
import com.oliverw.engine.gfx.ImageTile;
import com.oliverw.engine.gfx.RenderLayer;

public class Renderer
{
	private Font font = Font.STANDARD;
	
	private int pW, pH;
	private int[] p;
	
	private int[] zBuffer;
	private int zDepth = 0;
	
	public Renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		
		zBuffer = new int[p.length];
	}
	
	public void clear() {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
			
			zBuffer[i] = 0;
		}
	}
	
	public void setPixel(int x, int y, int value) {
		int alpha = (value >> 24) & 0xff;
		
		if ((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0) {
			return;
		}
		
		if (zBuffer[x + y * pW] > zDepth) {
			return;
		}
		
		if (alpha == 255) {
			p[x + y * pW] = value;
		} else {
			int pixelColour = p[x + y * pW];
			int newRed = ((pixelColour >> 16) & 0xff) - (int)((((pixelColour >> 16) & 0xff) - (value >> 16) & 0xff) * (alpha / 255f));
			int newGreen = ((pixelColour >> 8) & 0xff) - (int)((((pixelColour >> 8) & 0xff) - (value >> 8) & 0xff) * (alpha / 255f));
			int newBlue = ((pixelColour) & 0xff) - (int)((((pixelColour) & 0xff) - (value & 0xff)) * (alpha / 255f));
			
			
			p[x + y * pW] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
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
