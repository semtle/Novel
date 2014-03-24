import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Game {

	/*
	 * ゲームの進み方
	 * 
	 * タイトル
	 * 
	 * キャラセレクト
	 * 
	 * 散策場所の選択
	 * 
	 * スクリプトモード
	 * 		オープニングスクリプト
	 * 		ランダムスクリプト
	 * 		エンディングスクリプト
	 * 
	 */
	
	private Screen activeScreen;
	private FadeLayer fadeLayer;
	
	public static final int HEIGHT = 480;
	public static final int WIDTH = 640;
	
	
	
	public Game(){
		activeScreen = new TitleScreen();
		fadeLayer = new FadeLayer();
	}
	
	public void onTick(Key key, Mouse mouse){
		//作り変え名jキャ生けない仕様まとめ
		//progressはgameクラスが持って、0 ~ 200まで動く
		//FadeLayerクラスは描画の難しい処理をまとめるだけ
		fadeLayer.onTick();
		
		if(fadeLayer.getProgress() == 0){
			activeScreen.onTick(key , mouse);
			if(activeScreen != activeScreen.getNextScreen()){
				//スクリーン移行しようとしてる！
				fadeLayer.start();
			}

		} else {
			//暗幕が下りてくる途中
			if(50 == fadeLayer.getProgress()){
				activeScreen = activeScreen.getNextScreen();
			}
		}
		
		if(key.isPress(KeyEvent.VK_ESCAPE)){/*強制リセット処理*/
			activeScreen = new TitleScreen();	
		}
	}
	
	public void paint(Graphics g){
		activeScreen.paint(g);
		fadeLayer.paint(g);
		
	}
}
