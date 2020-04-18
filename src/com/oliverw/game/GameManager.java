package com.oliverw.game;

import java.awt.event.KeyEvent;

import com.oliverw.engine.AbstractGame;
import com.oliverw.engine.GameContainer;
import com.oliverw.engine.Renderer;
import com.oliverw.engine.gfx.Image;
import com.oliverw.engine.gfx.ImageTile;
import com.oliverw.engine.sfx.SoundClip;

public class GameManager extends AbstractGame
{
	private Image image;
	private ImageTile tiledImage;
	private SoundClip clip;
	
	public GameManager() {
		image = new Image("/apple.png");
		tiledImage = new ImageTile("/tiles.png", 16, 16);
		clip = new SoundClip("/untitled.wav");
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
			clip.play();
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
//		r.drawImageTile(image, gc.getInput().getMouseX(), gc.getInput().getMouseY(), 0, 0);
		int startX = 30;
		int startY = 30;
		int offset = 4;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				r.drawImageTile(tiledImage, startX + x * (offset + tiledImage.getTileWidth()), startY + y * (offset + tiledImage.getTileHeight()), x, y);
			}
		}
		
		r.drawImage(image, gc.getInput().getMouseX() - 16, gc.getInput().getMouseY() - 16);
		
		r.drawText("Test", 90, 90, 0xffa85c0c);
		r.drawRect(100, 40, 50, 30, 0xffff0000, 0xff00ff00);
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
