package com.oliverw.engine;

public class Transform
{
	public static final Transform DEFAULT = new Transform(0, 0, 0);
	
	private int xPos, yPos;
	private int rotation;
	
	private Transform parent;
	
	public Transform(int xPos, int yPos, int rotation) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rotation = rotation;
		
		parent = null;
	}
	
	public void update() {
		
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public void addPos(int moveX, int moveY) {
		this.xPos += moveX;
		this.yPos += moveY;
	}
	
	public void setPos(int x, int y) {
		xPos = x;
		yPos = y;
	}

	public Transform getParent() {
		return parent;
	}

	public void setParent(Transform parent) {
		this.parent = parent;
	}
}
