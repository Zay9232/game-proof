package stuff;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage sprite;
	
	public SpriteSheet(BufferedImage ss) {
		this.sprite = ss;
	}

	public BufferedImage grabImage(int col, int row, int width, int height, int hoffset, int voffset) {
		BufferedImage img = sprite.getSubimage((row*width)-width+hoffset, (col*height)-height+voffset, width, height);
		return img;
	}
	
}
