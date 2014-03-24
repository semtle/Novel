import java.awt.Graphics;


public abstract class Screen {
	

	/**
	 * 次の画面を返す。
	 */
	abstract public Screen getNextScreen();
	
	abstract public void paint(Graphics g);

	abstract public void onTick(Key key, Mouse mouse);
}
