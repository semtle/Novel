import java.awt.Graphics;
import java.awt.Image;



/**
 * 画面上に配置される、クリック可能なオブジェクト
 * 当たり判定は指定画像の大きさと同じ
 * 描画アンカーは真ん中
 */
public class ClickObject {
	
	private Image img;
	private int x;
	private int y;
	
	public ClickObject(){
		
	}
	public ClickObject(Image img, int x, int y){
		super();
		this.img = img;
		this.x = x;
		this.y = y;
	}
		
	public void paint(Graphics g){
		int height = img.getHeight(null);
		int width = img.getWidth(null);

		g.drawImage(img, 
				x - width/2, y - height/2, x + width/2, y + height/2, 
				0,0,img.getWidth(null),img.getHeight(null),null);
		
	}
	/**
	 * マウス座標との当たり判定
	 */
	public boolean hitTest(Mouse mouse){
		int height = img.getHeight(null);
		int width = img.getWidth(null);

		if(x - width/2 < mouse.getX() && mouse.getX() < x + width/2 &&
				y - height/2 < mouse.getY() && mouse.getY() < y + height/2){
			return true;
		}
		return false;		
	}
	
}
