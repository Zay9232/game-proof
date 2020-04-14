package stuff;

import java.util.Random;

public class Spawner {

	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 0;
	
	public Spawner(Handler handler, HUD hud) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick() {
		scoreKeep++;
		
		if (scoreKeep >= 75) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel()+1);
			
			if (hud.getLevel() == 2) {
				handler.addObject(new eGoblin(400, 420, ID.Enemy, handler, 1));
			}
			/*
			if (hud.getLevel() == 5) {
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH-10) + 5, r.nextInt(Game.HEIGHT-20)+10, ID.SmartEnemy, handler));
			}
			
			*/
		}
	}
	
	
	

}
