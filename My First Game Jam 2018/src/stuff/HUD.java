package stuff;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

	public static float HEALTH;

	
	private int score = 0;
	private int level = 1;
	
	public static float multi;
	
	public HUD() {
		HEALTH = Game.HPmax;
		multi = 200/HEALTH;
	}

	public void tick() {
		//HEALTH--;
		
		HEALTH= Game.clamp(HEALTH, 0 , Game.HPmax);
		
		score++;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(15, 15, 200, 32);
		g.setColor(Color.GREEN);
		g.fillRect(15, 15, (int) (HEALTH*multi), 32);
		
		g.drawString("Money: " + Game.money, 15, 64);
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void dead() {
		HEALTH = Game.HPmax;
		score = 0;
		level = 1;
		
	}
	
}
