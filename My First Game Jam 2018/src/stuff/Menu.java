package stuff;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import stuff.Game.STATE;

public class Menu extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	public Menu(Game game, Handler handler, HUD hud
			) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (game.gameState == STATE.Menu) {
			//Play Button
			if(mouseOver(mx, my, 270, 240, 200, 64)) {
				game.gameState = STATE.Game;
				handler.clearEnemies();
				game.generateRoom1();
				
				System.out.println(Game.HPmax);
				HUD.HEALTH = Game.HPmax;
				HUD.multi = 200/HUD.HEALTH;
				System.out.println(HUD.HEALTH);
			}
			
			if (mouseOver(mx, my, 270,348, 200, 64 )) {
				game.gameState = STATE.shop;
			}
			
			//Quit Button
			if (mouseOver(mx,my, 270, 490, 200, 64)) {
				System.exit(1);
			}
		}
		
		else if (game.gameState == STATE.End) {
			if (mouseOver(mx,my,270,495,200,64)) {
				game.gameState = STATE.Menu;
				hud.dead();
				//for (int i=0; i<15;i++) {
				//	handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
				//}
			}
		}
		
		else if (game.gameState == STATE.shop) {
			if (mouseOver(mx, my, game.WIDTH/2-45, Game.HEIGHT - Game.HEIGHT/4, 150 , 64)){
				game.gameState = STATE.Menu;
			}
			
			}
			if (mouseOver(mx, my, Game.WIDTH/3-175+Game.WIDTH/3, Game.HEIGHT/2-40, 150, 64)) {
				if (game.money >= game.healthCost) {
					Game.HPmax += 20;
	
					Game.money -= game.healthCost;
					if (Game.healthCost < 600) Game.healthCost += 100;
					else Game.healthCost *= 2;
				}
			}


	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x+width) {
			if (my > y && my < y + height) {
				return true;
			}else return false;
		} else return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("arial",1,45);
		Font fnt2 = new Font("arial",1,30);
		Font fnt3 = new Font("arial",1,20);
		
		if (game.gameState == STATE.shop) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Money:  " + game.money, Game.WIDTH/2-120, 100);
			g.setFont(fnt3);

			
			g.drawString("Upgrade", Game.WIDTH/3-150+Game.WIDTH/3, Game.HEIGHT/2);
			g.drawString("Health + 20", Game.WIDTH/3-150+Game.WIDTH/3, Game.HEIGHT/2-55);
			g.drawString("Cost: " + Game.healthCost, Game.WIDTH/3-150+Game.WIDTH/3, Game.HEIGHT/2+64);
			g.drawRect(Game.WIDTH/3-175+Game.WIDTH/3, Game.HEIGHT/2-40, 150, 64);
			
			
			g.drawRect(game.WIDTH/2-50, Game.HEIGHT - Game.HEIGHT/4, 150, 64);
			g.drawString("Menu", game.WIDTH/2-5, Game.HEIGHT - Game.HEIGHT/4+40);
			
		}
		
		if (game.gameState == STATE.Menu) {
			g.setFont(fnt);
			g.setColor(Color.white);
			//g.drawString("Menu", Game.WIDTH/2-70, 140);
			g.drawImage(Game.logo, 160,140, null);
			g.setFont(fnt2);
			g.drawString("Play", Game.WIDTH/2-40, Game.HEIGHT/4+48+32+46);
			g.drawString("Shop", Game.WIDTH/2-34, Game.HEIGHT/4+48+32+46 + 130);
			g.drawString("Quit", Game.WIDTH/2-40, Game.HEIGHT/4+48+32+46 + 130 + 126);
			
			
			g.setColor(Color.white);
			g.drawRect(270, 240, 200, 64);
			g.drawRect(270, 370, 200, 64);
			g.drawRect(270, 495, 200, 64);
		}
		else if (game.gameState == STATE.End) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Game Over", 200, 70);
			
			g.setFont(fnt2);
			g.drawString("You died with a Score of: " + hud.getScore(), 150, 150);
			
			
			g.drawString("Main Menu", 294, 540);
			g.setColor(Color.white);
			g.drawRect(270, 495, 200, 64);
		}
		else if (game.gameState == STATE.Win) {
			g.setColor(Color.white);
			g.setFont(fnt2);
			g.drawString("You have vanquished", Game.WIDTH/2-200, Game.HEIGHT/2-50);
			g.drawString("Crazy Pete the Goblin Wizard", Game.WIDTH/2-200, Game.HEIGHT/2+10);
		}
		
	}
	
	
}
