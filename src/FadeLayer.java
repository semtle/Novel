import java.awt.Color;
import java.awt.Graphics;



/**
 * 暗幕
 */

public class FadeLayer {

	
	//暗幕の動いている方向
	private int progress;

	public void paint(Graphics g){
		//状況によって暗幕をペイント
		int x = progress;
		if(50 < x ){
			//50以降は反転していく
			x = 100 - x;
		}
		
		if(0 < x){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0,(int)((Game.WIDTH  * x) / 50), Game.HEIGHT);
		}
	}
	public void onTick(){
		if(0 < progress){
			progress += 4;//KARI
			if(100 < progress){
				progress = 0;
			}
		}
	}
	public int getProgress() {
		return progress;
	}
	public void start() {
		progress = 2;
	}
}
