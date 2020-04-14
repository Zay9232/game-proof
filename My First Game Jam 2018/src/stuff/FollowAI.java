package stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class FollowAI extends GameObject {

	private ArrayList<Color> colors = new ArrayList<Color>(8);
	private Random r = new Random();
	private Handler handler;
	private Color color;
	
	private GameObject player;
	
	public FollowAI(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		spdY = 5;
		spdX = 5;
		this.handler = handler;
		
		for (int i = 0; i < handler.objects.size();i++) {
			if (handler.objects.get(i).getID() == ID.Player) player = handler.objects.get(i);
		}
		
		colors.add(Color.BLUE);
		colors.add(Color.CYAN);
		colors.add(Color.GREEN);
		colors.add(Color.RED);
		colors.add(Color.ORANGE);
		colors.add(Color.PINK);
		colors.add(Color.white);
		colors.add(Color.YELLOW);
	}

	@Override
	public void tick() {
		x+= spdX;
		y += spdY;

		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float) Math.sqrt(Math.pow(x-player.getX(), 2)+(Math.pow(y-player.getY(), 2)));
		
		spdX = (float) ((-1.0/distance)*diffX);
		spdY = (float) ((-1.0/distance)*diffY);
		
		if (y<= 0 || y>= Game.HEIGHT-32) spdY *= -1;
		if (x<= 0 || x>= Game.WIDTH-16) spdX *= -1;
	
		handler.addObject(new Trail(x,y,ID.Trail,16,16,0.09f,randomColor(),handler));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(randomColor());
		g.fillRect((int)x,(int) y, 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,16,16);
	}

	public Color randomColor() {
		
		int i = r.nextInt(8);
		return colors.get(i);
		
	}
	
}
