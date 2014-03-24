
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * マウス関連イベントを受け取って、マウス状態を保管するクラス。
 * @author yuta
 *
 */
public class Mouse implements MouseListener , MouseMotionListener{
	
	private int x,y;
	private final boolean DEBUG = false;
	
	private boolean mouseDown;
	private boolean prevMouseDown;
	private boolean nextMouseDown;
	
	public Mouse(){
		x = y = 0;
		mouseDown = false;
		prevMouseDown = false;
	}
	

	public void updateInput() {
		prevMouseDown = mouseDown;
		mouseDown = nextMouseDown;
	}

	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(DEBUG) System.out.println("Drag");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(DEBUG) System.out.println("Move");
		
		x = (int)e.getPoint().getX();
		y = (int)e.getPoint().getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		nextMouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		nextMouseDown = false;		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isDown(){
		return this.mouseDown;
	}
	/**
	 * マウスが押し込まれたときにオン
	 */
	public boolean onPress(){
		return this.mouseDown && !this.prevMouseDown;
	}
	/**
	 * マウスが離された時にオン
	 */
	public boolean onRelease(){
		return !this.mouseDown && this.prevMouseDown;		
	}

}
