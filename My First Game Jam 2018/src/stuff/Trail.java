package stuff;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Trail extends GameObject {


	
	private float alpha = 1;
	
	private Handler handler;
	private Color color;
	
	private float width, height;
	private float life;
	
	public Trail(float x, float y, ID id, float width, float height, float life ,Color color ,Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
	
	}

	@Override
	public void tick() {
		if (alpha > life) {
			alpha -= life - 0.00001f;
		} else handler.removeObject(this);
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.setColor(color);
		g.fillRect((int)x,(int)y,(int)width,(int)height);
		
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}

}
