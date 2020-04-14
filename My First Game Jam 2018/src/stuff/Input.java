package stuff;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter{

	private Handler handler;
	private boolean[] keyDown = {false,false,false,false};
	int w,s,a,d,x,y,spd=4;
	
	public Input(Handler handler) {
		this.handler = handler;
		
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0; i < handler.objects.size();i++) {
			GameObject temp = handler.objects.get(i);
			
			if (temp.getID() == ID.Player) {
				
				//if (key == KeyEvent.VK_W) w = 1;//{temp.setSpdY(1); keyDown[0]=true;}
				//if (key == KeyEvent.VK_S) s = 1;//{temp.setSpdY(1); keyDown[1]=true;}
				if (key == KeyEvent.VK_D) d = 1;//{temp.setSpdX(1); keyDown[2]=true;}
				if (key == KeyEvent.VK_A) a = 1;//{temp.setSpdX(1); keyDown[3]=true;}
				if (key == KeyEvent.VK_SPACE) temp.attacked = true;
				if (key == KeyEvent.VK_W) if(temp.jumping == false) temp.jumped = true;
				//if (key == KeyEvent.VK_SHIFT) temp.setY(200);
				
				
				
				x=d-a;
				//y=s-w;
				
				temp.setSpdX(x*spd);
				//temp.setSpdY(y*spd);
				
			}
			
			
			
			if (key == KeyEvent.VK_ESCAPE) System.exit(1);
			//System.out.println(key);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0; i < handler.objects.size();i++) {
			GameObject temp = handler.objects.get(i);
			
			if (temp.getID() == ID.Player) {
				
				//if (key == KeyEvent.VK_W) w=0; //keyDown[0] = false; //temp.setSpdY(0);
				if (key == KeyEvent.VK_A) a=0;//keyDown[3] = false;//temp.setSpdX(0);
				//if (key == KeyEvent.VK_S) s=0;//keyDown[1] = false;//temp.setSpdY(0);
				if (key == KeyEvent.VK_D) d=0;//keyDown[2] = false;//temp.setSpdX(0);
			
				//if (!keyDown[0] && !keyDown[1]) temp.setSpdY(0);
				if (!keyDown[2] && !keyDown[3]) temp.setSpdX(0);
			
				x=d-a;
				//y=s-w;
				
				temp.setSpdX(x*spd);
				//temp.setSpdY(y*spd);
				
			}
		}
	}
}
