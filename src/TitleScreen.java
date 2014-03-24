import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class TitleScreen extends Screen {

	private boolean isEnd;
	private Image kyaraImg;
	private int imgx;
	private int imgy;
	
	public TitleScreen() {
		isEnd = false;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(ImageLiblary.getImage(ImageLiblary.TITLE),60 , 60, null);
		g.drawImage(ImageLiblary.getImage(ImageLiblary.CAUTION),0 , 240, null);
		g.drawImage(ImageLiblary.getImage(8),10 , 40, null);
/*		g.drawString("たいとる", 120, 120);
		g.drawString("くりっくですたーと", 200, 230);*/
		g.drawImage(kyaraImg,imgx,imgy,null);
	}

	@Override
	public Screen getNextScreen() {
		if(isEnd){
			return new SelectScreen();
		} else {
			return this;
		}
	}

	@Override
	public void onTick(Key key, Mouse mouse) {
		
		
		if(mouse.onPress()){
			isEnd = true;
		}
	}
}
