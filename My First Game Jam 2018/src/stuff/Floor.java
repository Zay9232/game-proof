package stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Floor extends GameObject {

	private Color color;
	private int sizex,sizey, imagex, imagey;
	private BufferedImage g_default;
	
	public Floor(int xsize, int ysize, int x, int y, ID id) {
		super(x, y, id);
		this.color = color;
		this.sizex = xsize;
		this.sizey = ysize;
		g_default = Game.groundTILES.grabImage(1,2,32,32,4,2);
		imagex=32;
		imagey=32;
	}

	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {

		g.drawImage(g_default, (int)x, (int)y, null);
		
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x,(int)y,sizex,sizey);
	}
	
	
	public void setSizex(int sizex) {
		this.sizex=sizex;
	}
	public void setSizey(int sizey) {
		this.sizey=sizey;
	}
	
	
	public int getSizex() {
		return sizex;
	}
	public int getSizey () {
		return sizey;
	}
	

}
