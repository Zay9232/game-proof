package stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import stuff.GameObject.Anim;

public class ProjectileBossAlly extends GameObject {

	private Random r = new Random();
	private Handler handler;
	private Color color;
	private int Hp;
	private GameObject boss;
	
	public ProjectileBossAlly(float x, float y, ID id, Handler handler, float spdMod, float hp) {
		super(x, y, id);
		spdY = 2*spdMod;
		spdX = 2*spdMod;
		this.handler = handler;
		Hp = (int) hp;
		color = Color.MAGENTA;
		findBoss();
	}

	@Override
	public void tick() {
		collision();
		
		
		
		if (id == ID.Enemy) {
			x+= spdX;
			y += spdY;
		}
		else {
			if(x > boss.getX()) {
				if (spdX > 0) spdX *= -1;
			}
			if (x < boss.getX()) {
				if (spdX < 0) spdX *= -1;
			}
			
			if (y > boss.getY()) {
				if (spdY>0) y*=-1;
			}
			if (y < boss.getY()) {
				if (spdY<0) y*=-1;
			}
			
			x+= spdX;
			y += spdY;
		}
		

		if (y<= 0 || y>= Game.HEIGHT-32) spdY *= -1;
		if (x<= 0 || x>= Game.WIDTH-16) spdX *= -1;
	
		handler.addObject(new Trail(x,y,ID.Trail,12f,12f,0.09f,color,handler));

	}

	@Override
	public void render(Graphics g) {

		g.setColor(color);
		g.drawRect((int)x, (int)y, 12, 12);

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x,(int)y,12,12);
	}
	
	private void collision() {
		for (int i=handler.objects.size()-1;i>=0;i--) {
			GameObject temp = handler.objects.get(i);
			if (temp.getID() == ID.Hitbox && id == ID.Enemy) {
				if (temp.getBounds().intersects(getBounds())) {
					if (spdY>0) spdY*=-1;
					color = Color.BLUE;
					id = ID.EnemyFriend;
					break;
				}
			}
			if (temp.getID() == ID.Boss && id == ID.EnemyFriend) {
				if (temp.getBounds().intersects(getBounds())) {
					temp.attacked = true;
					handler.removeObject(this);
				}
			}
		}
	}
	
	private void findBoss() {
		for (int i=0;i<=handler.objects.size()-1;i++) {
			GameObject temp = handler.objects.get(i);
			if (temp.getID() == ID.Boss) boss = temp;
			
		}
	}

}
