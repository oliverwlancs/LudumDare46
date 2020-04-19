package com.oliverw.game;

import java.awt.event.KeyEvent;

import com.oliverw.engine.GameContainer;
import com.oliverw.engine.Renderer;
import com.oliverw.engine.gfx.ImageTile;

public class Player extends GameObject
{	
	private int tileX, tileY;
	private float offX, offY;
	
	private float moveSpeed = 200;
	private float fallSpeed = 10;
	
	private float jumpHeight = -4;
	
	private float fallDistance = 0;
	
	private boolean grounded = false;
	
	public Player(int xPos, int yPos) {
		this.tag = "Player";
		this.tileX = xPos;
		this.tileY = yPos;
		this.offX = 0;
		this.offY = 0;
		this.xPos = xPos * GameManager.TILE_SIZE;
		this.yPos = yPos * GameManager.TILE_SIZE;
		this.width = 16;
		this.height = 16;
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		if (gc.getInput().isKey(KeyEvent.VK_D)) {
			if (gm.getCollision(tileX + 1, tileY) || gm.getCollision(tileX + 1, tileY + (int)Math.signum((int)offY))) {
				if (offX < 0) {
					offX += dt * moveSpeed;
				}
				
				if (offX > 0) {
					offX = 0;
				}
			} else {
				offX += dt * moveSpeed;
			}
		}
		
		if (gc.getInput().isKey(KeyEvent.VK_A)) {
			if (gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1, tileY + (int)Math.signum((int)offY))) {
				if (offX > 0) {
					offX -= dt * moveSpeed;
				}
				
				if (offX < 0) {
					offX = 0;
				}
			} else {
				offX -= dt * moveSpeed;
			}
		}
		
		
		
		fallDistance += dt * fallSpeed;
		
		if (gc.getInput().isKeyDown(KeyEvent.VK_SPACE) && grounded) {
			fallDistance = jumpHeight;
			grounded = false;
		}
		
		grounded = false;
		
		offY += fallDistance;
		
		if (fallDistance < 0) {
			if ((gm.getCollision(tileX, tileY - 1) || gm.getCollision(tileX + (int)Math.signum((int)offX), tileY - 1)) && offY < 0) {
				fallDistance = 0;
				offY = 0;
			}
		}
		
		if (fallDistance > 0) {
			if ((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int)Math.signum((int)offX), tileY + 1)) && offY > 0) {
				fallDistance = 0;
				offY = 0;
				grounded = true;
			}
		}
		
		if (offY > GameManager.TILE_SIZE / 2) {
			tileY ++;
			offY -= GameManager.TILE_SIZE;
		}
		
		if (offY < -GameManager.TILE_SIZE / 2) {
			tileY --;
			offY += GameManager.TILE_SIZE;
		}
		
		if (offX > GameManager.TILE_SIZE / 2) {
			tileX ++;
			offX -= GameManager.TILE_SIZE;
		}
		
		if (offX < -GameManager.TILE_SIZE / 2) {
			tileX --;
			offX += GameManager.TILE_SIZE;
		}
		
		xPos = tileX * GameManager.TILE_SIZE + offX;
		yPos = tileY * GameManager.TILE_SIZE + offY;
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(new ImageTile("/Assets/Art/Player.png", 16, 16).getImageTile(0, 0), (int)xPos, (int)yPos);
		
	}
}
