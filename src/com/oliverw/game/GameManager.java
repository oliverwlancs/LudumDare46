package com.oliverw.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.oliverw.engine.AbstractGame;
import com.oliverw.engine.Camera;
import com.oliverw.engine.GameContainer;
import com.oliverw.engine.Renderer;
import com.oliverw.engine.gfx.Image;
import com.oliverw.engine.gfx.RenderLayer;

public class GameManager extends AbstractGame
{
	private ArrayList<RenderLayer> renderLayers;
	
	private Camera camera;
	
	private PlayerController player;
	
	public GameManager() {
		camera = new Camera();
		
		renderLayers = new ArrayList<RenderLayer>();
		
		player = new PlayerController();
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		for (RenderLayer rL : renderLayers) {
			for (Image image : rL.getImages()) {
				r.drawImage(image, (int)image.getParent().getxPos(), (int)image.getParent().getyPos());
			}
		}
	}
	
	public void addRenderLayer(RenderLayer rL) {
		renderLayers.add(rL);
		sortRenderLayers();
	}
	
	public void sortRenderLayers() {
		boolean sorted = true;
		for (int i = 1; i < renderLayers.size(); i++) {
			if (renderLayers.get(i).getzIndex() < renderLayers.get(i - 1).getzIndex()) {
				sorted = false;
			}
		}
		
		if (sorted) {return;}
		
		Collections.sort(renderLayers, new Comparator<RenderLayer>() {
		    @Override
		    public int compare(RenderLayer o1, RenderLayer o2) {
		    	return Integer.compare(o1.getzIndex(), o2.getzIndex());
		    }
		});
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
