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


public class KyaraDisplay {

	public final int HEIGHT = 320;
	public final int WIDTH = 240;
	
	boolean flip;
	
	private int changeProgress;
	
	private Image img;
	private Image prevImg;
	public void setImage(String name){
		//XXX 空っぽの画像を用意しないとね
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
		if(flip){
			g.drawImage(img, ox+WIDTH, oy, ox, oy+HEIGHT, 0, 0, WIDTH,HEIGHT, null);
		} else {
			g.drawImage(img, ox, oy, null);
		}
		
		
		
	}
	public boolean isChangeing(){
		return changeProgress > 0;
	}
}
