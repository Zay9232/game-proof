package stuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	public static final int WIDTH = 740, HEIGHT = 640;
	
	private Thread thread;
	private boolean running = false;
	
	private Random r = new Random();
	private Handler handler;
	private HUD hud;
	private Spawner spawner;
	private Menu menu;
	public static int money = 0;
	public static int healthCost = 100;
	public static float HPmax = 100;
	public static int Max = 300;
	
	//Character Animation stuff
	public static BufferedImage groundTiles;
	public static BufferedImage c_idle;
	public static BufferedImage c_walk;
	public static BufferedImage c_attack;
	public static BufferedImage BOSS;
	public static BufferedImage logo ;
	public static SpriteSheet groundTILES;
	public static SpriteSheet c_ATTACK;
	public static SpriteSheet c_IDLE;
	public static SpriteSheet c_WALK;
	public static SpriteSheet Boss;
	public static ArrayList<BufferedImage> idle = new ArrayList<BufferedImage>(4);
	public static ArrayList<BufferedImage> walk = new ArrayList<BufferedImage>(8);
	public static ArrayList<BufferedImage> attack = new ArrayList<BufferedImage>(10);
	public static ArrayList<BufferedImage> boss = new ArrayList<BufferedImage>(13);
	
	public static BufferedImage gob_Walk;
	public static SpriteSheet gob_WALK;
	public static ArrayList<BufferedImage> gWalk = new ArrayList<BufferedImage>();
	
	public static int room = 0;
	
	public enum STATE{
		Menu,
		Game,
		shop,
		End,
		Win
	};
	
	//Player generation
	
	
	public void generateFloor(int sizex, int sizey, int tWidth, int tHeight) {
		//Generates floor;
		for(int a=0; a<=sizex; a+= tWidth) {
			for (int b=HEIGHT; b>=sizey; b-= tHeight) {
				handler.addObject(new Floor(tWidth, tHeight, a, b, ID.Floor));
			}
		}
	}
	public void generatePlatform(int x1, int x2, int y1, int y2, int tWidth, int tHeight) {
		for(int a=x1; a<=x2; a+= tWidth) {
			for (int b=y1; b>=y2; b-= tHeight) {
				handler.addObject(new Floor(tWidth, tHeight, a, b, ID.Floor));
			}
		}
	}
	public void generateRoom1() {
		generateFloor(Game.WIDTH, 544, 32, 32);
		//generatePlatform(Game.WIDTH-128,Game.WIDTH, 440, 440, 32, 32);
		//generatePlatform(0,128, 440, 440, 32, 32);
		generatePlatform(WIDTH/2-64, WIDTH/2+64, 200,200, 32, 32);
		handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2, ID.Player, handler));
		handler.addObject(new eGoblin(32, 400, ID.Goblin, handler, 1));
		handler.addObject(new eBoss(WIDTH/2 - (35/2), 200-80, ID.Boss, handler));
	}
	
	
	public static STATE gameState = STATE.Menu;
	
	public Game() {
		System.out.println(1 % 3);
		System.out.println(3 % 1);
		
		handler = new Handler();
		hud = new HUD();
		spawner = new Spawner(handler, hud);
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new Input(handler));
		this.addMouseListener(menu);
		
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop(1, .2f);
		//AudioPlayer.getMusic("oof")

		ImageLoader loader = new ImageLoader();
		c_idle = loader.loadImage("/spr_cIDLE.png");
		c_walk = loader.loadImage("/spr_cWALK.png");
		c_attack = loader.loadImage("/spr_cATTACK.png");
		gob_Walk = loader.loadImage("/gWalk.png");
		groundTiles = loader.loadImage("/ground.png");
		BOSS = loader.loadImage("/boss.png");
		c_IDLE = new SpriteSheet(c_idle);
		c_WALK = new SpriteSheet(c_walk);
		c_ATTACK = new SpriteSheet(c_attack);
		gob_WALK = new SpriteSheet(gob_Walk);
		groundTILES = new SpriteSheet(groundTiles);
		Boss = new SpriteSheet(BOSS);
		logo = loader.loadImage("/logo1.png");
		
		for (int i=1;i<=4;i++) {
			idle.add(c_IDLE.grabImage(1, i, 42, 42,0,0));
		}
		for (int i=1;i<=8;i++) {
			walk.add(c_WALK.grabImage(1, i, 42, 42,0,0));
		}
		for (int i=1;i<=10;i++) {
			attack.add(c_ATTACK.grabImage(1, i, 42, 42,0,0));
		}
		for (int i=1;i<=6;i++) {
			gWalk.add(gob_WALK.grabImage(1, i, 20, 32,0,0));
		}
		for (int i=1; i<=13; i++){
			boss.add(Boss.grabImage(1, i, 70, 80, 0, 0));
		}
		
		new window(WIDTH, HEIGHT, "Crazy Pete the Goblin Wizard", this);

		if (gameState == STATE.Game) {
			
		}
		
		
		else if (gameState == STATE.Menu) {
			for (int i=0; i<15;i++) {
				//handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
			}
		}
		
		
	}
	
	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return var = max;
		}
		else if (var <= min) {
			return var = min;
		}
		else{
			return var;
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running){
				render();
			}
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
			
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
		if (gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			if (hud.HEALTH <= 0) {
				gameState = STATE.End;
				handler.clearEnemies();
				AudioPlayer.getSound("oof").play(1, .8f);

			}
		}
		if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.shop || gameState== STATE.Win) {
			handler.clearEnemies();
			menu.tick();
		}

	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if (gameState == STATE.Game) {
			hud.render(g);
		}
		if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.shop || gameState== STATE.Win) {
			menu.render(g);
		}
		
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
	public float getHP() {
		return HPmax;
	}

}
