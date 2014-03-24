import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.text.StringCharacterIterator;
import java.util.Scanner;


public class ScenarioScreen extends Screen {

	private ScenarioProgress progress;
	
	private ScriptPlayer scriptplayer;
	
	//場所選択に表示するもの
	private int currentPlace = -1;
	private ClickObject[] places;

	private FadeLayer fadeLayer;
	
	private boolean isEnd;
	
	public static final int LIMIT_DAY = 8;
	
	private static final String[] placeName = {
		"メインストリート",
		"広場",
		"農場",
		"クモの仕立て屋",
		"ロッシ電気店",
		"マキの家",
		"ロロアの家"
	};
	public static final int
	STREET = 0,
	HIROBA = 1,
	NOUJO = 2,
	KUMO = 3,
	BLOSH = 4,
	MAKI = 5,
	ROLOA = 6,
	PLACENUM = 7;	
		
	
	
	public ScenarioScreen() {
		this.scriptplayer = new ScriptPlayer();		
		this.fadeLayer = new FadeLayer();

		//場所選択画面に表示するもの
		this.places = new ClickObject[PLACENUM];
		this.places[STREET] = new ClickObject(ImageLiblary.getImage(ImageLiblary.PLACE),
				222,270);
		this.places[HIROBA] = new ClickObject(ImageLiblary.getImage(ImageLiblary.PLACE),
				150,350);
		this.places[NOUJO] = new ClickObject(ImageLiblary.getImage(ImageLiblary.PLACE),
				391,164);
		this.places[KUMO] = new ClickObject(ImageLiblary.getImage(ImageLiblary.PLACE),
				320,320);
		this.places[BLOSH] = new ClickObject(ImageLiblary.getImage(ImageLiblary.PLACE),
				271,190);
		this.places[MAKI] = new ClickObject(ImageLiblary.getImage(ImageLiblary.PLACE),
				117,172);
		this.places[ROLOA] = new ClickObject(ImageLiblary.getImage(ImageLiblary.PLACE),
				407,270);

	}	
	/**
	 * ふつうはこのコンストラクタから始まる
	 */
	public ScenarioScreen(ScenarioProgress scenarioProgress) {
		this();
		this.progress = scenarioProgress;
	}

	@Override
	public Screen getNextScreen() {
		if(this.isEnd){
			return new TitleScreen();
		}
		return this;
	}

	@Override
	public void paint(Graphics g) {
				
		if(this.scriptplayer.isPlaying()){
			scriptplayer.paint(g);
		} else if(1 <= progress.getDay() && progress.getDay() < LIMIT_DAY){
			//散策場所選択
			g.drawImage(ImageLiblary.getImage(ImageLiblary.MAP) , 20 , 40 , null);
			for(int i=0 ; i < places.length ; i++){
				places[i].paint(g);
			}
			if(this.currentPlace >= 0){
				g.setColor(Color.black);
				g.drawString(placeName[currentPlace],200,400);
			}
			
			//主人公ちびキャラを表示
			switch(progress.getHero()){
			case Blosh:
				g.drawImage(ImageLiblary.getImage(ImageLiblary.BLOSH), 500, 300, null);
				break;
			case Kumo:
				g.drawImage(ImageLiblary.getImage(ImageLiblary.KUMO), 500, 300, null);
				break;
			case Maki:
				g.drawImage(ImageLiblary.getImage(ImageLiblary.MAKI), 500, 300, null);
				break;
			case Roloa:
				g.drawImage(ImageLiblary.getImage(ImageLiblary.ROLOA), 500, 300, null);
				break;	
			}
			//残り日数を表示
			g.setColor(Color.black);
			g.drawString("カーニバルまであと "+ (LIMIT_DAY - progress.getDay()) +"日", 300, 50);
			
		}
		fadeLayer.paint(g);
	}

	@Override
	public void onTick(Key key, Mouse mouse) {
		fadeLayer.onTick();

		if(fadeLayer.getProgress() == 0 ){
			//フェードレイヤーが動いている間は何もしない
			if(this.scriptplayer.isPlaying()){
				//スクリプトプレイヤーを動かす
				scriptplayer.onTick(mouse, key, progress,fadeLayer);
			} else {
				//スクリプトプレイが終了していたら			
				if(progress.getDay() == 0){
					//初日はキャラに対応したオープニングシナリオを見せることにする
					if(progress.getHero() == KyaraNum.Roloa){
						//ロロアが主人公の場合
						scriptplayer.load("RoloaOpen.txt");							
					} else if(progress.getHero() == KyaraNum.Maki){

						scriptplayer.load("MakiOpen.txt");							
						
					} else {
						//それ以外の場合（？）
						scriptplayer.load("random00.txt");
					}	
				} else if(progress.getDay() == LIMIT_DAY){
					//最終日は好感度を計算して相手に対応したエンディングシナリオを見せる
					
					if(progress.getHero() == KyaraNum.Roloa){
						//ロロアが主人公の場合
						if(progress.getLoveBlosh() > progress.getLoveMaki()){
							scriptplayer.load("ロロアxブロッシュ.txt");
						} else {
							scriptplayer.load("ロロアxマキ.txt");							
						}
					} else if(progress.getHero() == KyaraNum.Maki){
						if(progress.getLoveRoloa() > progress.getLoveKumo()){
							scriptplayer.load("マキxロロア.txt");
						} else {
							scriptplayer.load("マキxクモ.txt");							
						}
						
					} else {
						//それ以外の場合（？）
						scriptplayer.load("random00.txt");
					}
					
					
				} else if(LIMIT_DAY < progress.getDay()){
					// シナリオ終了
					this.isEnd = true;
				} else {
					//それ以外なら、散策場所選択スクリーンを出して散策場所を選ばせる
					//System.out.println(mouse.getX() +":"+ mouse.getY());
					
					//各場所との当たり判定を取って最初に当たったものをカレントプレースに
					currentPlace = -1;
					for(int i=0 ; i < places.length ; i++){
						if(places[i].hitTest(mouse)){
							currentPlace = i;
							break;
						}
					}
					
					if(mouse.onPress() && 0 <= currentPlace){
						//this.scriptplayer.loadRandom(progress, currentPlace);
						fadeLayer.start();
					}
				}
			}
		} else if(fadeLayer.getProgress() == 50){
			//このタイミングで実際の画面切り替えを行う
			if(this.scriptplayer.isPlaying()){
				this.scriptplayer.setPlaying(false); //XXX 暗転がスクリプト終了のトリガーになるとかちょっと
			} else {
				this.scriptplayer.loadRandom(progress, currentPlace);
			}
			
		}
		
	}

}
