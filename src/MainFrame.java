
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;



public class MainFrame extends Frame implements Runnable, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1;
	private Key key;	//キー
	private Mouse mouse; //マウス
	private Game game;
	private Image offImage;
	private Graphics offg;
	private FPSKeeper fpskeeper;

	public static void main(String[] args){
		
		new MainFrame().start();
	}
	
	public MainFrame(){
		super();
	}
	
	
	public void start() {
		
		//閉じたときの動作
		this.addWindowListener(new MyWindowListener());
		//ウィンドウ座標設定
		this.setSize(640,480);
		this.setLocation(160,80);
		
		//フォーカス可能に
		this.setFocusable(true);
		//キーリスナーの登録
		key = new Key();
		this.addKeyListener(key);
		mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		
		ImageLiblary.loadImage();
		
		this.game = new Game();

		this.setVisible(true);
		offImage = this.createImage(640,480);
		this.offg = offImage.getGraphics();
		
		//フレームの時間を教えるためのタイマーを用意
		//Timer timer = new Timer(0,this);
		//timer.start();

		fpskeeper = new FPSKeeper();
		new Thread(this).start();		

	}
	
	//@Override
	public void actionPerformed(ActionEvent arg0) {
		//1フレームの処理・描画をする
		this.key.updateInput(); //キー入力の更新
		this.mouse.updateInput(); //マウス入力の更新
		game.onTick(this.key , this.mouse);
		
		this.repaint();
	}
	@Override
	public void run() {
		for(;;){
			fpskeeper.waitForFPS();
			this.actionPerformed(null);
		}
	}
	
	
	public void update(Graphics g){
		this.paint(g);
	}
	public void paint(Graphics g){
		super.paint(g);
		
		offg.clearRect(0, 0, 640, 480);
		game.paint(offg);
		//fpskeeper.paint(offg, 20, 20);
		
		offg.setColor(Color.white);
		
		g.drawImage(offImage, 0, 0, this);
	}

	

}
