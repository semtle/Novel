import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.awt.image.BufferedImageOp;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class BackGroundDisplay {

	public final int HEIGHT = 640;
	public final int WIDTH = 480;
		
	private int changeProgress;
	
	private Image img;
	private Image prevImg;
	public void setImage(String name){
		
		changeProgress = 0;
		
		prevImg = img;
		
		Toolkit TK = Toolkit.getDefaultToolkit();
		img = TK.getImage("img/" + name + ".PNG");
		
			//画像読み方法２
			//InputStream is = new FileInputStream();
			//ImageIO.read(is);
			//is.close();
	}
	public void onTick(){
		if(this.isChangeing()){
			changeProgress--;
		}
	}
	public void paint(Graphics g,int ox,int oy){
			g.drawImage(img, ox, oy, null);		
	}
	public boolean isChangeing(){
		return changeProgress > 0;
	}
}
