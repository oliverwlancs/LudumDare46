package com.oliverw.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.oliverw.engine.AbstractGame;
import com.oliverw.engine.GameContainer;
import com.oliverw.engine.Renderer;
import com.oliverw.engine.gfx.Image;
import com.oliverw.engine.gfx.ImageTile;
import com.oliverw.engine.gfx.RenderLayer;

public class GameManager extends AbstractGame
{
	public static final int TILE_SIZE = 16;
	
	private ArrayList<RenderLayer> renderLayers;
	
	private ArrayList<GameObject> objects;
	
	private ArrayList<boolean[]> collision;
	private int levelWidth;
	private int levelHeight;
	
	private ImageTile ground = new ImageTile("/Assets/Art/Ground Tiles.png", 16, 16);
	private ArrayList<int[]> permutations = new ArrayList<int[]>();
	private ArrayList<Image> tiles = new ArrayList<Image>();
	private int[] tileIndices = new int[256];
	private int[] tileOffsets = {0, 1, 1, 1, 0, -1, -1, -1};
//	
//	private Camera camera;
//	
//	private PlayerController player;
//	
//	public RenderLayer playerLayer;
	
	public GameManager() {
		objects = new ArrayList<GameObject>();
		collision = new ArrayList<boolean[]>();
		
		objects.add(new Player(4, 3));
		
		loadLevel("/Assets/Art/LevelTest.png");
		
		/*0*/permutations.add(new int[] {28,30,60,156,158,188,190,62});
		/*1*/permutations.add(new int[] {124,126,252,254});
		/*2*/permutations.add(new int[] {112,114,120,240,250,122,242,248});
		/*3*/permutations.add(new int[] {4,6,12,36,132,14,38,134,44,140,164,46,142,166,172,174});
		/*4*/permutations.add(new int[] {68,70,76,100,196,78,102,198,108,204,228,110,206,230,236,238});
		/*5*/permutations.add(new int[] {64,96,192,66,72,224,98,104,194,200,74,226,232,106,202,234});
		/*6*/permutations.add(new int[] {16,24,48,144,18,56,152,26,176,50,146,184,58,154,178,186});
		/*7*/permutations.add(new int[] {31,159,63,191});
		/*8*/permutations.add(new int[] {255});
		/*9*/permutations.add(new int[] {241,249,243,251});
		/*10*/permutations.add(new int[] {0,2,8,10,32,34,40,42,128,130,136,138,160,162,168,170});
		/*11*/permutations.add(new int[] {247});
		/*12*/permutations.add(new int[] {223});
		/*13*/permutations.add(new int[] {17,25,49,145,19,57,153,27,177});
		/*14*/permutations.add(new int[] {7,135,39,15,167,47,175,143});
		/*15*/permutations.add(new int[] {199,231,207,239});
		/*16*/permutations.add(new int[] {195,193,225,201,233,203,235,227});
		/*16*/permutations.add(new int[] {85});
		/*17*/permutations.add(new int[] {253});
		/*18*/permutations.add(new int[] {127});
		/*19*/permutations.add(new int[] {1,129,3,9,33,131,137,161,11,35});
		/*20*/permutations.add(new int[] {116,118,244,246});
		/*21*/permutations.add(new int[] {209,217,211,219});
		/*22*/permutations.add(new int[] {84,86,212,214});
		/*23*/permutations.add(new int[] {81,89,83,91});
		/*24*/permutations.add(new int[] {20,22,52,148,150,180,54,182});
		/*25*/permutations.add(new int[] {80,88,208,82,90,210,216,218});
		/*26*/permutations.add(new int[] {221});
		/*27*/permutations.add(new int[] {29,157,61,189});
		/*28*/permutations.add(new int[] {71,103,79,111});
		/*29*/permutations.add(new int[] {21,149,53,181});
		/*30*/permutations.add(new int[] {69,101,77,109});
		/*31*/permutations.add(new int[] {5,133,13,37,165,45,141,173});
		/*32*/permutations.add(new int[] {65,97,67,73,105,75,99,107});
		/*33*/permutations.add(new int[] {119});
		/*34*/permutations.add(new int[] {92,94,220,222});
		/*35*/permutations.add(new int[] {113,121,115,123});
		/*36*/permutations.add(new int[] {215});
		/*37*/permutations.add(new int[] {95});
		/*38*/permutations.add(new int[] {213});
		/*39*/permutations.add(new int[] {87});
		/*40*/permutations.add(new int[] {23,151,55,183});
		/*41*/permutations.add(new int[] {197,229,205,237});
		/*42*/permutations.add(new int[] {245});
		/*43*/permutations.add(new int[] {125});
		/*44*/permutations.add(new int[] {117});
		/*45*/permutations.add(new int[] {93});
		
		for (int i = 0; i < permutations.size(); i++) {
			tiles.add(ground.getImageTile(i % (ground.getWidth() / ground.getTileWidth()), (int)Math.floor(i / (ground.getWidth() / ground.getTileWidth()))));
		};
		
		for (int i = 0; i < permutations.size(); i++) {
			for(int index : permutations.get(i)) {
				tileIndices[index] = i;
			}
		}
	}
	
	@Override
	public void init(GameContainer gc) {
		
	}
	
	@Override
	public void update(GameContainer gc, float dt) {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).update(gc, this, dt);
			if (objects.get(i).isDead()) {
				objects.remove(i);
				i--;
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
//		for (RenderLayer rL : renderLayers) {
//			for (Image image : rL.getImages()) {
//				r.drawImage(image, (int)image.getParent().getxPos(), (int)image.getParent().getyPos());
//			}
//		}
		for (int y = levelHeight - 1; y >= 0; y--) {
			for (int x = 0; x < levelWidth; x++) {
				if (getCollision(x, y)) {
					int index = 0;
					for (int i = 0; i < tileOffsets.length; i++) {
						if (getCollision(x + tileOffsets[i], y + tileOffsets[(i + 6) % 8])) {
							index += (1 << i);
						}
					}
					r.drawImage(tiles.get(tileIndices[index]), x * TILE_SIZE, y * TILE_SIZE);
				}
			}
		}
		for (GameObject obj : objects) {
			obj.render(gc, r);
		}
	}
	
	public void loadLevel(String path) {
		Image levelImage = new Image(path);
		
		levelWidth = levelImage.getWidth();
		levelHeight = levelImage.getHeight();
		
		
		
		for (int y = levelHeight - 1; y >= 0; y--) {
			boolean[] collisionLayer = new boolean[levelWidth];
			for (int x = 0; x < levelWidth; x++) {
				if (levelImage.getP()[x + y * levelWidth] == 0xff000000) {
					collisionLayer[x] = true;
				} else {
					collisionLayer[x] = false;
				}
			}
			collision.add(collisionLayer);
		}
	}
	
	public boolean getCollision(int x, int y) {
		y = (GameContainer.SCREEN_HEIGHT / 16) - (y + 1);
		
		if(y < 0 || y >= collision.size() || x < 0 || x >= levelWidth) {
			return true;
		}
		return collision.get(y)[x];
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
