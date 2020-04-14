package stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class MenuParticle extends GameObject {

	private Random r = new Random();
	private Handler handler;
	private Color color, col;
	
	public MenuParticle(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		spdY = r.nextInt(6)+2;
		spdX = r.nextInt(6)+2;
		this.handler = handler;
		
		col = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
		

		
	}

	@Override
	public void tick() {
		x+= spdX;
		y += spdY;

		if (y<= 0 || y>= Game.HEIGHT-32) spdY *= -1;
		if (x<= 0 || x>= Game.WIDTH-16) spdX *= -1;
	
		handler.addObject(new Trail(x,y,ID.Trail,16f,16f,0.07f,col,handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,16,16);
	}

	
}
