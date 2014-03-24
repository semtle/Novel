import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class SelectScreen extends Screen {

	private boolean click;
	private Screen next;
	
	//キャラ識別番号を作るのがめんどいゴリ押し
	private ClickObject maki;
	private ClickObject kumo;
	private ClickObject roloa;
	private ClickObject blosh;
	
	private boolean onMaki;
	private boolean onKumo;
	private boolean onRoloa;
	private boolean onBlosh;

	public SelectScreen() {
		next = this;
		maki = new ClickObject(ImageLiblary.getImage(ImageLiblary.MAKI),320,60);
		kumo = new ClickObject(ImageLiblary.getImage(ImageLiblary.KUMO),60,240);
		roloa = new ClickObject(ImageLiblary.getImage(ImageLiblary.ROLOA),580,240);
		blosh = new ClickObject(ImageLiblary.getImage(ImageLiblary.BLOSH),320,420);
	}
	
	@Override
	public Screen getNextScreen() {
		return next;
	}

	@Override
	public void paint(Graphics g) {

		//当たり判定によってキャラのプロフィールと立ち絵を表示
		if(onMaki){
			g.drawImage(ImageLiblary.getImage(ImageLiblary.MAKIPROF),100 , 100,null);
		} else if(onKumo){
			g.drawImage(ImageLiblary.getImage(ImageLiblary.KUMOPROF),100 , 100,null);			
		} else if(onRoloa){
			g.drawImage(ImageLiblary.getImage(ImageLiblary.ROLOAPROF),100 , 100,null);			
		} else if(onBlosh){
			g.drawImage(ImageLiblary.getImage(ImageLiblary.BLOSHPROF),100 , 100,null);
		} else {
			g.drawImage(ImageLiblary.getImage(ImageLiblary.CHOOSE),0 , 0,null);			
		}
		
		g.setColor(Color.black);
		
		//キャラのボタン（ちびキャラ）を書く
		maki.paint(g);
		kumo.paint(g);
		roloa.paint(g);
		blosh.paint(g);

		
	}

	@Override
	public void onTick(Key key, Mouse mouse) {
		
		onMaki = maki.hitTest(mouse);
		onKumo = kumo.hitTest(mouse);
		onRoloa = roloa.hitTest(mouse);
		onBlosh = blosh.hitTest(mouse);
		
		if(mouse.onPress()){
			if(onMaki){
				next = new ScenarioScreen(new ScenarioProgress(KyaraNum.Maki));
			} else if(onKumo){
				
			} else if(onRoloa){
				next = new ScenarioScreen(new ScenarioProgress(KyaraNum.Roloa));
			} else if(onBlosh){
				
			}


		}		
	}

}
