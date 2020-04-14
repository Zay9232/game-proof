package stuff;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	//protected int sizex,sizey;
	protected ID id;
	protected float x,y,spdX, spdY;
	protected boolean jumping = false, jumped=false,attacked=false,hitStun = false;
	protected float health;
	public enum Anim{
		Idle,
		Walk,
		Attack,
		left,
		right;
	}
	
	public GameObject(float x, float y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	//public void takeDamage() {
	//}
	
	public void setX(float x) {
		this.x =x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void setSpdX(float x) {
		this.spdX = x;
	}
	public void setSpdY(float y) {
		this.spdY = y;
	}	
	public void setID(ID id) {
		this.id = id;
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getSpdX() {
		return x;
	}
	public float getSpdY() {
		return y;
	}
	public ID getID() {
		return id;
	}
}
