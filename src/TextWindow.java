import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class TextWindow {

	private String text;
	private String text2;
	private String name;
	private Font messageFont;
	
	private boolean isHide;
	
	public TextWindow(){
		text = "";
		text2 = "";
		isHide = false;
		
		name = "";
		messageFont = new Font("メイリオ",Font.BOLD , 20);
	}
	
	public void paint(Graphics g , int ox, int oy){

		if(!this.isHide){
			g.setFont(messageFont);
			g.setColor(Color.black);
	
			g.fillRect(ox,oy,640,120);
			g.drawImage(ImageLiblary.getImage(4), ox, oy-50, null);
			//メッセージウィンドう
			/*g.setColor(Color.black);
			g.setColor(Color.white);
			g.fillRect(ox + 10, oy + 10, 620, 100);
	
			*/
			
			//名前欄
			g.drawString(name, ox + 30, oy - 10);
			g.drawString(text, ox + 30, oy + 40);
			g.drawString(text2, ox + 30, oy + 70);
			
		}
	}

	public void clear(){
		text = "";
		text2 = "";
		name = "";
		this.isHide = false;
	}
	public void hide(){
		this.isHide = true;
	}
	
	public void add( char ch ) {
		if(this.text.length() < 25){
			this.text += ch;
		} else {
			this.text2 += ch;
		}
	}

	public void setNameWindow(String substring) {
		this.name = substring;
	}
}
