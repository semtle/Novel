
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * キーイベントを受け取ってキー状態を保管するクラス。
 * 一応トグル判定とかも取れる。
 * 
 * @author yuta
 */

public class Key implements KeyListener{

	private static final int KEY_NUM = 256;
	
	private boolean keyStateBuffer[];
	private boolean keyStateNow[];
	private boolean keyStateBefore[];	
	
	public Key(){
		super();
		keyStateBuffer = new boolean[KEY_NUM];
		keyStateNow = new boolean[KEY_NUM];
		keyStateBefore = new boolean[KEY_NUM];
		for(int i=0 ; i < KEY_NUM ; i++){
			keyStateBefore[i] = false;
			keyStateNow[i] = false;
			keyStateBuffer[i] = false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < KEY_NUM){
			keyStateBuffer[e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < KEY_NUM){
			keyStateBuffer[e.getKeyCode()] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	/**
	 * キーの入力状況をアップデートする。
	 */
	public void updateInput(){
		for(int i=0 ; i < KEY_NUM ; i++){
			keyStateBefore[i] = keyStateNow[i];
			keyStateNow[i] = keyStateBuffer[i];
		}
		
	}
	/**
	 * 引数のキーが押されているかどうか
	 */
	public boolean isDown(int keycode){
		if(keycodeCheck(keycode))
			return keyStateNow[keycode];
		else
			return false;
	}
	/**
	 * 引数のキーが押された瞬間かどうか
	 */
	public boolean isPress(int keycode){
		if(keycodeCheck(keycode))
			return keyStateNow[keycode] && !keyStateBefore[keycode];
		else
			return false;
		
	}
	/**
	 * 引数のキーが離された瞬間かどうか
	 */
	public boolean isRelease(int keycode){
		if(keycodeCheck(keycode))
			return !keyStateNow[keycode] && keyStateBefore[keycode];
		else
			return false;

	}
	
	/**
	 * キー入力関数を使うときにoutOfBound防止
	 */
	public static boolean keycodeCheck(int keycode){
		return 0 <= keycode && keycode < KEY_NUM;
	}
}
