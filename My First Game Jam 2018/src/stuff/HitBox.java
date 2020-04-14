package stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class HitBox extends GameObject {
	
	Handler handler;
	private int sizex, sizey, damage, duration;
	private float px, py;
	private Player p;

	public HitBox(float x, float y, ID id, Handler handler, int sizex, int sizey, int damage, int duration, Player player) {
		super(x, y, id);
		this.handler = handler;
		this.sizex = sizex;
		this.sizey = sizey;
		this.damage = damage;
		this.duration = duration;
		this.p = player;
	}

	@Override
	public void tick() {
		updatePos();
		x = px;
		y = py;
		
		
		if (duration <= 0) handler.removeObject(this);
		duration --;
	}

	@Override
	public void render(Graphics g) {
		if (duration > 0) {
			//g.setColor(Color.PINK);
			//g.drawRect((int)x, (int)y, sizex, sizey);
		}
		
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y,sizex,sizey);
	}
	

	
	public void updatePos() {
		px = p.getX();
		py = p.getY();
		if (p.getDirection() == Anim.right) px+= 22;
		if (p.getDirection() == Anim.left) px -= 8;
		
	}

}
