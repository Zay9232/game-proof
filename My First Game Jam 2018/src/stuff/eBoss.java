package stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import stuff.Game.STATE;

public class eBoss extends GameObject {

	Random r = new Random();
	Handler handler;
	private int oneTime = 400;
	private int wave =0, Health = 5, waveTimer=0, waveSpawner=500, waveChoice = 0;
	private float difficulty = 0f;
	private int minionCount = 3, missileCount=5 ,enemyCount=0, currentImage=0, maxImage = 0;
	private int vmissle = 3, hmissle = 3;
	private int animTimer;
	private boolean newWave = false;
	private ID currentWave;
	private int Hp = 5
			;
	private Anim current=Anim.Idle;
	public static float missleDamage = 1f;
	
	public eBoss(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}

	@Override
	public void tick() {
		
		if (oneTime <= 0) {
			
			//Check for wave start
			if (waveTimer==waveSpawner) newWave =true;
			if (enemyCount == 0) newWave = true;
			
			//Wave start
			if (newWave == true) {
				if (current!=Anim.Attack) {
					current = Anim.Attack;
					currentImage = 0;
					maxImage = 12;
					
				}
				if (currentImage == 6) {
					System.out.println(wave);
					if (wave == 4 || wave == 8 || wave == 12 || wave == 16 || wave == 20) {
						HUD.HEALTH += 50;
					}
					wave++;
					if (wave == 4 || wave == 8 || wave == 12 || wave == 16 || wave == 20) {
						System.out.println("wave % 4 == " + wave%4);
						difficulty += .15;
						spawnMissles((int)(missileCount*(1+difficulty)));
						missileCount += 2;
						
					}
					else {
						difficulty += .075;
						waveChoice = r.nextInt(3);
						System.out.println("r = " +waveChoice);
						
						if (waveChoice == 0) {
							
							System.out.println("gob wave");
							goblinWave((int) (minionCount*(1+difficulty)));
							minionCount += 1;
						}
						else if (waveChoice == 1) {
							System.out.println("v missiles");
							spawnVerticalMissles((int) (vmissle*(1+difficulty)));
							vmissle+=1;
						}
						else if (waveChoice == 2) {
							System.out.println("h missiles");
							spawnHorizontalMissles((int) (hmissle*(1+difficulty)));
							hmissle += 1;
						}
						
					}
					
					waveTimer = 0;
					waveSpawner = (int) (750 / (1+difficulty));
					newWave = false;
					missleDamage = 1 * difficulty;
				}
				

			}
			else {
				currentImage = 0;
				maxImage = 0;
				current = Anim.Idle;
			}
			
			
			checkClear(currentWave);
			waveTimer++;
			
			
			if (attacked == true) {
				Hp -= 1;
				attacked = false;
				removeEnemies();
			}
			
			
		}
		oneTime--;
		
		
		if (Hp <=0) {
			Game.gameState = STATE.Win;
			Game.money += 2000;
			
		}

	}



	@Override
	public void render(Graphics g) {
		if(currentImage > maxImage) currentImage = 0;

	
		//HealthBar
		g.setColor(Color.BLACK);
		g.fillRect(525,15,200,32);
		g.setColor(Color.ORANGE);
		g.fillRect(525, 15, Hp*40, 32);
		
		g.drawImage(Game.boss.get(currentImage), (int)x, (int)y, null);
		
		

		if(animTimer == 300) {
			animTimer = 0;
			currentImage++;
			
		}
		
		animTimer++;
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int)x, (int)y, 70, 80);
	}

	public void checkClear(ID current) {
		for (int i=0;i<handler.objects.size()-1;i++) {
			GameObject temp = handler.objects.get(i);
			
			if (temp.getID() == current) {
				enemyCount +=1;
			}
			if(temp.getID() == current) {
				enemyCount+=1;
			}
			
		}
	}
	
	public void removeEnemies() {
		for (int i=handler.objects.size()-1;i>=0;i--){
			GameObject temp = handler.objects.get(i);
			if (temp.getID() == ID.Enemy) {
				handler.removeObject(temp);
			}
		}
	}
	
	public void goblinWave(int spawnCount) {
		if (spawnCount > 10) spawnCount = 10;
		currentWave = ID.Goblin;
		for (int i=0;i<spawnCount;i++) {
			handler.addObject(new eGoblin((r.nextInt(Game.WIDTH)), 420, ID.Goblin, handler, (1+difficulty)));
		}
	}
	
	public void spawnMissles(int spawnCount) {
		if (spawnCount > 11) spawnCount = 11;
		currentWave = ID.Enemy;
		for (int i=0; i<spawnCount; i++) {
			handler.addObject(new ProjectileBoss((float)r.nextInt(Game.WIDTH), 60f, ID.Enemy, handler, 1+difficulty, 10*(1+difficulty),0));
		}
		
		handler.addObject(new ProjectileBossAlly((float)r.nextInt(Game.WIDTH), 60f, ID.Enemy, handler, 1+difficulty, 10*(1+difficulty)));
	}
	
	public void spawnVerticalMissles(int spawnCount) {
		if (spawnCount > 9) spawnCount = 9;
		currentWave = ID.Enemy;
		int spacing = Game.WIDTH/spawnCount;
		for (int i=0; i < Game.WIDTH;i+= spacing) {
			handler.addObject(new ProjectileBoss(i+1, 0+2, ID.Enemy, handler, 1+difficulty, (10*(1+difficulty))/2,7));
		}
	}
	private void spawnHorizontalMissles(int spawnCount) {
		if (spawnCount > 9) spawnCount =9;
		currentWave = ID.Enemy;
		int spacing = Game.HEIGHT/spawnCount;
		for (int i=0; i < Game.HEIGHT; i+= spacing) {
			handler.addObject(new ProjectileBoss(0+2, i+1, ID.Enemy, handler, 1+difficulty, (10*(1+difficulty))/2,8));
		}
	}
	
}
