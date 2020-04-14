package stuff;

import java.awt.Graphics;
import java.util.LinkedList;

import stuff.Game.STATE;

import java.util.ArrayList;

public class Handler {

	ArrayList<GameObject> objects = new ArrayList<GameObject>();

	
	public void tick() {
		for(int i=0; i< objects.size();i++) {
			GameObject temp = objects.get(i);
			
			temp.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i=0; i< objects.size();i++) {
			GameObject temp = objects.get(i);
			
			temp.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
	public void removeObject(GameObject object) {
		objects.remove(object);
	}
	
	public void clearEnemies() {
		for (int i=objects.size()-1;i>=0;i--) {
			if (Game.gameState != STATE.End) {
				if (objects.get(i).getID() != ID.Player) {
					objects.remove(i);
				}
			}
			else {
				objects.clear();
			}
			
		}
	}

}
