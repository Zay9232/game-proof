package stuff;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class Player extends GameObject {

	private Random r;
	Handler handler;
	private float grav, jumpspd=-10f, lastx=0;
	private boolean grounded = false, hitbox = false;
	private int currentImage=0, maxImage=3, timingCounter=0, attackCooldown=10, attackTimer=0, attackCount = 1;;
	private int animationTimer=500, printTimer=100;
	private int groundedCount=0;
	
	private Anim current = Anim.Idle;
	private Anim direction = Anim.right;
	
	
	
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		grav = .4f;
	}
	
	public void tick() {	
		
		groundedCount=0;
		collision();
		
		
		if (printTimer == 100) {
			printTimer = 0;
			//System.out.println(grounded);
		}
		printTimer++;
		
		if (spdX >0) direction = Anim.right;
		if (spdX <0) direction = Anim.left;
		
		if (attacked == true && attackTimer <= 0) {
			attackTimer = 100000;
			current = Anim.Attack;
			maxImage = 9;
			attacked = false;
			animationTimer = 275;
			currentImage = 0;
		}
		if (current == Anim.Attack && currentImage == 5 && attackCount ==1) {
			attack();
			attackCount--;
		}

		//Jumping
		if(groundedCount == 0) {
			grounded = false;
			spdY += grav;
		}
		else {
			grounded = true;
			spdY = 0;
		}
		if (jumped == true) {
			jump();
			grounded = false;

		}
		
		
		

		
		
		
		//Setting Animations
		if (current != Anim.Attack) {
			if (lastx!=spdX) {
				if (spdX != 0) {
					current = Anim.Walk;
					if (spdX >0) direction = Anim.right;
					if (spdX <0) direction = Anim.left;
					currentImage = 0;
					//Animation length
					maxImage = 7;
					animationTimer = 500;
				}
				else {
					current = Anim.Idle;
					currentImage = 0;
					//Animation length
					maxImage = 3;
					animationTimer = 500;
				}
			}
		}
		
		if (current == Anim.Attack) {
			if (spdX!=0) {
				if(direction==Anim.right) spdX=1.5f;
				else spdX=-1.5f;
			}
			
		}
		else if (spdX != 0){
			if(direction==Anim.right) spdX=3f;
			else spdX=-3f;
		}
		
		
		x += spdX;
		y += spdY;
		lastx = spdX;
		attackTimer--;
		
		x = Game.clamp(x,-10,Game.WIDTH-20);
		y = Game.clamp(y,0,Game.HEIGHT-70);
		jumped = false;
	}

	public void render(Graphics g) {
		if (currentImage > maxImage) {
			if (current == Anim.Attack) {
				attacked = false;
				attackTimer = attackCooldown;
				attackCount = 1;
				if (spdX!=0) {
					current = Anim.Walk;
					maxImage = 7;
					animationTimer = 500;
				}
				else {
					current = Anim.Idle;
					maxImage = 3;
					animationTimer = 500;
				}
			}
			currentImage = 0;
		}
		
		//g.setColor(Color.white);
		//g.fillRect((int)x,(int) y, 32, 32);
		if (current == Anim.Idle) {
			if (direction==Anim.right)g.drawImage(Game.idle.get(currentImage),(int)x,(int)y,null);
			if (direction==Anim.left)g.drawImage(Game.idle.get(currentImage), (int)x+42, (int)y, -42, 42, null);
		}
		else if (current == Anim.Walk) {
			if (direction == Anim.right) g.drawImage(Game.walk.get(currentImage),(int)x,(int)y,null);
			if (direction == Anim.left) g.drawImage(Game.walk.get(currentImage), (int)x+42, (int)y, -42, 42, null);
		}
		else if(current == Anim.Attack) {
			if (direction == Anim.right) g.drawImage(Game.attack.get(currentImage), (int)x, (int)y, null);
			if (direction == Anim.left) g.drawImage(Game.attack.get(currentImage), (int)x+42, (int)y, -42, 42, null);
		}
		

		//timingCounter slows down the animation the higher it is
		timingCounter++;
		if (timingCounter >= animationTimer) {
			currentImage++;
			timingCounter = 0;
		}
		else {
			timingCounter++;
		}
	}
	
	public Rectangle getBounds() {
		if (direction == Anim.right) return new Rectangle((int)x,(int)y,26,42);
		else return new Rectangle((int)x,(int)y,26,42);
	}
	public Rectangle getBoundsFeet() {
		if (direction == Anim.right) return new Rectangle((int)x,(int)y+42,26,42);
		else return new Rectangle((int)x,(int)y+42,26,42);
	}
	
	private void collision() {
		for (int i=0;i<handler.objects.size()-1;i++) {
			GameObject temp = handler.objects.get(i);
			
			if (temp.getID() == ID.Enemy || temp.getID() == ID.SmartEnemy || temp.getID() == ID.Goblin) {
				if (getBounds().intersects(temp.getBounds())) {
					if (temp.hitStun==false); HUD.HEALTH-=eBoss.missleDamage;
				}
			}
				if (temp.getID() == ID.Floor) {
					if (temp.getBounds().intersects(getBounds())) {
						groundedCount+=1;
					}
					
				}
			
			
		}
	}
	
	private void jump() {
		if (grounded == true) {
			spdY = jumpspd;
			grounded = false;
		}
	}
	
	public void attack() {
		if (direction == Anim.right) handler.addObject(new HitBox(x+22,y, ID.Hitbox,handler, 23, 40, 1, 16, this));
		if (direction == Anim.left) handler.addObject(new HitBox(x-8,y, ID.Hitbox,handler, 23, 40, 1, 16, this));
	}
	
	public Anim getDirection() {
		
		if (direction == Anim.right) return Anim.right;
		else return Anim.left;
		
	}
	
	
}
