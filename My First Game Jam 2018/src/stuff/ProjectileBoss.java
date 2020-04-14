package stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class ProjectileBoss extends GameObject {

	private Random r = new Random();
	private Handler handler;
	private Color color;
	private int Hp;
	private int xy;
	
	
	public ProjectileBoss(float x, float y, ID id, Handler handler, float spdMod, float hp, int xy) {
		super(x, y, id);
		int dir = r.nextInt(1);
		if (dir==0) dir = 1;
		else dir = -1;

		int spd = r.nextInt(3)+3;
		this.xy = xy;
		
		if (xy==0) {
			spdY = spd*spdMod*dir;
			spdX = spd*spdMod*dir;
		}
		else {
			spdY = xy;
			spdX = xy;
		}
		
		this.handler = handler;
		this.Hp =(int) hp;
		System.out.println(Hp);
	}

	@Override
	public void tick() {
		if (xy == 8) {
			x += spdX;
		}
		if (xy == 7) {
			y += spdY;
		}
		else {
			x+=spdX;
			y+= spdY;
		}
		
		
		
		if (y<= 0 || y>= Game.HEIGHT-32) {
			spdY *= -1;
			Hp--;
		}
		if (x<= 0 || x>= Game.WIDTH-16) {
			spdX *= -1;
			Hp --;
		}
		
	
		if (Hp <= 0) {
			handler.removeObject(this);
		}
		
		handler.addObject(new Trail(x,y,ID.Trail,12f,12f,0.09f,Color.RED,handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 12, 12);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,12,12);
	}

	
}
