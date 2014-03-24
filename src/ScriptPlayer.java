import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.StringCharacterIterator;
import java.util.Scanner;


public class ScriptPlayer{
	
	//シナリオ画面に表示されるもの
	private TextWindow textwindow;
	private KyaraDisplay kyara1;
	private KyaraDisplay kyara2;
	private BackGroundDisplay background;

	private Scanner scriptScanner;
	private StringCharacterIterator textIterator;
	private boolean isPause;
	private boolean isPlaying;

	//周回中既読スクリプトのログ
	private int[] scriptlog;
	
	public static final int RANDOM_SCENARIO_NUM = 70;
	
	
	
	public ScriptPlayer(){

		this.scriptlog = new int[10];
		
		//スクリプト中に表示するものの準備
		this.textwindow = new TextWindow();
		this.kyara1 = new KyaraDisplay();
		this.kyara2 = new KyaraDisplay();
		this.kyara2.flip = true;
		this.background = new BackGroundDisplay();
		this.textIterator = new StringCharacterIterator("");

		
	}
	
	/**
	 * 場所と進行状況をもらってランダムスクリプトを読み込む
	 */
	public void loadRandom(ScenarioProgress progress, int place){
		//とりあえずフラグ立て
		this.isPlaying = true;
		
		//現在の状況をデバプリ
		progress.printState();
		
		for(int i=0 ; i < 100 ; i++){
			//ランダムにスクリプトを取る
			//sprintfほしー
			int num = (int)(Math.random() * RANDOM_SCENARIO_NUM);
			//TODO シナリオかぶりがないようにする
			if(kaburiCheck(num)){
					
	
				
				String numstr = String.format("%02d", num);
				System.out.println("ランダムシナリオ"+numstr);
				try {
					this.scriptScanner = new Scanner(new File("story/" + numstr + ".txt" ),"UTF-8");
				} catch (FileNotFoundException e) {
					System.out.println("そもそもファイルがない。");
					continue;
				}
				
				//何回読んでも再生可能なスクリプトが無いなら、場所に対応した無人スクリプトをロードする。
				if(scenarioCheck(progress, place)){
					scriptlog[progress.getDay()] = num;
					return;
				}
				this.scriptScanner.close();
			}
		}
		try {
			this.scriptScanner = new Scanner(new File("story/" + "00.txt" ),"UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("random00が見つからないとかいう究極致命エラー");
			System.exit(9999);
		}
				
	}
	public boolean kaburiCheck(int scenarioNum){
		for(int i=0 ; i < scriptlog.length ; i++){
			if(scriptlog[i] == scenarioNum){
				return false;
			}
		}
		return true;
	}
	
	public boolean scenarioCheck(ScenarioProgress progress, int place){
		for(;;){
			if(this.scriptScanner.hasNext()){
				//スクリプトファイルを一ブロック読んで
				String str = this.scriptScanner.next();
				if(str.charAt(0) == '@'){ //制御文なら
					str = str.substring(1);
					if(str.equals("start")){ //再生許可
						//@start 再生許可
						System.out.println("再生を許可する！");
						return true;
					} else if(str.equals("ifplace")){ //場所判定
						System.out.println("場所判定");
						String placename = placeNumToName(place);
						if(!placename.equals(scriptScanner.next())){
							System.out.println("場所が違う");
							return false;
						}
					} else if(str.equals("ifmember")){
						System.out.println("メンバー条件");
						String members = scriptScanner.nextLine();
						if(members.indexOf(progress.getHeroName()) == -1){
							System.out.println("メンバー条件が不適合");
							return false;
						}
					} else if(str.equals("iflovemaki")){ //好感度
						if(progress.getHero() != KyaraNum.Maki){
							System.out.println("マキの好感度");
							if(progress.getLoveMaki() < scriptScanner.nextInt()){
								System.out.println("足りない");
								return false;
							}
						}
					} else if(str.equals("ifloveroloa")){
						if(progress.getHero() != KyaraNum.Roloa){
							System.out.println("ロロアの好感度");	
							if(progress.getLoveRoloa() < scriptScanner.nextInt()){
								System.out.println("足りない");
								return false;
							}
						}
					} else if(str.equals("iflovekumo")){
						if(progress.getHero() != KyaraNum.Kumo){
							System.out.println("クモの好感度");
							if(progress.getLoveKumo() < scriptScanner.nextInt()){
								System.out.println("足りない");
								return false;
							}						
						}
					} else if(str.equals("ifloveblosh")){
						if(progress.getHero() != KyaraNum.Blosh){
							System.out.println("ロッシの好感度");
							if(progress.getLoveBlosh() < scriptScanner.nextInt()){
								System.out.println("足りない");
								return false;	
							}
						}
					} else if(str.equals("niflovemaki")){ //好感度条件逆ヴァージョン
						if(progress.getHero() != KyaraNum.Maki){
							System.out.println("マキの好感度");
							if(progress.getLoveMaki() > scriptScanner.nextInt()){
								System.out.println("大杉");
								return false;
							}
						}
					} else if(str.equals("nifloveroloa")){
						if(progress.getHero() != KyaraNum.Roloa){
							System.out.println("ロロアの好感度");
							if(progress.getLoveRoloa() > scriptScanner.nextInt()){
								System.out.println("大杉");
								return false;
							}
						}
					} else if(str.equals("niflovekumo")){
						if(progress.getHero() != KyaraNum.Kumo){
							System.out.println("クモの好感度");
							if(progress.getLoveKumo() > scriptScanner.nextInt()){
								System.out.println("大杉");
								return false;
							}						
						}
					} else if(str.equals("nifloveblosh")){
						if(progress.getHero() != KyaraNum.Blosh){
							System.out.println("ロッシの好感度");
							if(progress.getLoveBlosh() > scriptScanner.nextInt()){
								System.out.println("大杉");
								return false;	
							}
						}
					} else if(str.equals("")){
						
					} else {
						System.out.println("未定義の制御文" + str);
					}
				} else {
					//テキストなら
					System.out.println("制御文じゃないもの:" + str);
				}
			} else {
				//スクリプトがもうなければ
				this.scriptScanner.close();
				System.err.println("@startがないシナリオファイルをつかまされた。");
				System.exit(0);
			}		
		}
		
	}
	/**
	 * 指定されたスクリプトを読み込む
	 */
	public void load(String name){
		this.isPlaying = true;
		//ランダムにスクリプトを取る
		try {
			this.scriptScanner = new Scanner(new File("story/" + name ),"UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void onTick(Mouse mouse , Key key,ScenarioProgress progress , FadeLayer fadelayer){
		kyara1.onTick();
		kyara2.onTick();
		
		if(key.isPress(KeyEvent.VK_F1)){
			//強制終了
			fadelayer.start();
			progress.addDay(1);
		}
		
		//スクリプトプレイ中なら
		if(this.isPause){ //入力待ち中なら
			if(mouse.onRelease()){
				this.isPause = false;
				this.textwindow.clear();
			}
			
		}else if(kyara1.isChangeing()){
			//キャラ１の変化中
		} else if(kyara2.isChangeing()){
			//キャラ２の変化中
		}else if(background.isChangeing()){
			//背景の変化中	
		} else if(textIterator.current() != StringCharacterIterator.DONE){
			//テキスト読み上げイテレータにまだ文字がある				
			//テキストウィンドウに足していく
			this.textwindow.add(textIterator.current());
			textIterator.next();
		} else {
		//スクリプトがまだあれば
			if(this.scriptScanner.hasNext()){
				//スクリプトファイルを一ブロック読んで
				String str = this.scriptScanner.next();
				if(str.charAt(0) == '@'){ //制御文なら
					str = str.substring(1);
					if(str.equals("page")){
						this.isPause = true;
					} else if(str.equals("kyara1")){ //キャラクター画像変更
						this.kyara1.setImage(
						this.scriptScanner.next()
						);
					} else if(str.equals("kyara2")){
						this.kyara2.setImage(
						this.scriptScanner.next()
						);
					} else if(str.equals("background")){
						this.background.setImage(
						this.scriptScanner.next()
						);
					} else if(str.equals("end")){ //スクリプト正常終了
						this.textwindow.clear();
						this.kyara1.setImage("void");
						this.kyara2.setImage("void");
						this.background.setImage("void");
						
						fadelayer.start();
						progress.addDay(1);
						
					} else if(str.equals("loveroloa")){ //好感度変化
						progress.addLoveRoloa(Integer.parseInt(this.scriptScanner.next()));
					} else if(str.equals("loveblosh")){ 
						progress.addLoveBlosh(Integer.parseInt(this.scriptScanner.next()));	
					} else if(str.equals("lovemaki")){ 
						progress.addLoveMaki(Integer.parseInt(this.scriptScanner.next()));
					} else if(str.equals("lovekumo")){ 
						progress.addLoveKumo(Integer.parseInt(this.scriptScanner.next()));
					} else if(str.equals("hidetext")){
						this.textwindow.hide();
					} else if(str.equals("hoge")){
						
					} else {
						System.out.println("未定義の制御文" + str);
					}

					
				} else {
					//テキストなら
					//セリフかどうか判断
					int kakkoIndex = str.indexOf('「');
					if(kakkoIndex != -1){
						//セリフなら
						this.textwindow.setNameWindow(str.substring(0, kakkoIndex));
						str = str.substring(kakkoIndex + 1, str.length());
					} else {
						this.textwindow.setNameWindow("");						
					}
					//テキスト読み上げ用イテレータにぶち込む
					this.textIterator.setText(str);
					this.textIterator.setIndex(0);
				}
			} else {
				//スクリプトがもうなければ
				this.scriptScanner.close();
			}
		}

	}
	public void paint(Graphics g){
		//スクリプトプレイ中
		this.background.paint(g, 0, 0);
		
		g.setColor(Color.black);
		//g.drawString("しなりお", 40, 100);
		
		this.kyara1.paint(g, 400, 30);

		this.kyara2.paint(g, 0, 30);
		
		this.textwindow.paint(g , 0 , 350);

	}
	
	public boolean isPlaying(){
		return this.isPlaying;
	}

	public void setPlaying(boolean b) {
		this.isPlaying = b;
	}
	
	public String placeNumToName(int i){
		switch(i){
		case ScenarioScreen.STREET:
			return "street";
		case ScenarioScreen.HIROBA:
			return "hiroba";
		case ScenarioScreen.NOUJO:
			return "noujo";
		case ScenarioScreen.MAKI:
			return "makihome";
		case ScenarioScreen.BLOSH:
			return "bloshhome";
		case ScenarioScreen.ROLOA:
			return "roloahome";
		case ScenarioScreen.KUMO:
			return "kumohome";
		}
		return null;
	}
	
}