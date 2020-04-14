package stuff;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class eGoblin extends GameObject {
	Random r = new Random();
	Handler handler;
	private GameObject player;
	private float grav = .4f;
	private boolean grounded = false, inMap=true;
	private int currentImage=0, maxImage=5, currentTimer=0, animTimer=450, hitStunSpd=2, Hp = 2;
	private Anim current = Anim.Walk, direction=Anim.left;
	private int moneydrop;
	
	public eGoblin(float x, float y, ID id, Handler handler, float difficulty) {
		super(x, y, id);
		spdX = 1.5f;
		spdY = 0;
		this.handler = handler;
		findPlayer();
		moneydrop = (int) (r.nextInt(15)+10 * difficulty);
	}

	@Override
	public void tick() {
		collision();
		if (Hp==0) {
			Game.money += moneydrop;
			handler.removeObject(this);
		}
		
		if (hitStun==false) {
			if(x > player.getX()) {
				if (spdX > 0) spdX *= -1;
				direction = Anim.left;
			}
			if (x < player.getX()) {
				if (spdX < 0) spdX *= -1;
				direction = Anim.right;
			}
		}
		if (grounded == false) {
			spdY += grav;
		}
		else {
			spdY = 0;
		}
		y += spdY;
		x += spdX;
		
		if (inMap == true) {
			x = Game.clamp(x, -4, Game.WIDTH-24);
		}
		
		
	}
	
	@Override
	public void render(Graphics g) {
		if (currentImage > maxImage) {
			currentImage = 0;
		}
		
		
		if (direction == Anim.right) g.drawImage(Game.gWalk.get(currentImage), (int)x+5, (int)y, null);
		if (direction == Anim.left) g.drawImage(Game.gWalk.get(currentImage), (int)x+20, (int)y, -20, 32, null);
		
		if(currentTimer >= animTimer) {
			currentImage++;
			currentTimer=0;
		}
		else {
			currentTimer++;
		}
		
	} 
	

	
	private void collision() {
		for (int i=handler.objects.size()-1;i>=0;i--) {
			GameObject temp = handler.objects.get(i);
			if (temp.getID() == ID.Hitbox) {
				if (temp.getBounds().intersects(getBounds())) {
					takeDamage();
					break;
				}
			}	
			if (temp.getID() == ID.Floor) {
				if (temp.getBounds().intersects(getBounds())) {
					grounded = true;
					spdX = 1.5f;
					hitStun = false;
				}
			}
			
		}
	}

	private void takeDamage() {
		grounded = false;
		if (hitStun == false) {
			Hp--;
		}
		hitStun = true;
		spdY = -8;
		if (direction == Anim.right) spdX = -hitStunSpd;
		if (direction == Anim.left) spdX = hitStunSpd;
		
	}

	@Override
	public Rectangle getBounds() {
		if (direction == Anim.right) return new Rectangle((int)x, (int)y, 20, 36);
		else return new Rectangle((int)x, (int)y,20,36);
	}
	
	
	public void findPlayer() {
		for (int i=0;i<=handler.objects.size()-1;i++) {
			GameObject temp = handler.objects.get(i);
			if (temp.getID() == ID.Player) player = temp;
			
		}
	}

}
