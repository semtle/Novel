/**
 * ゲームの進行状態を記録する。
 * ・主人公
 * ・経過日数
 * ・各キャラ好感度
 */
public class ScenarioProgress {
	
	public ScenarioProgress() {
		hero = null;
		day = 0;
		loveRoloa = 0;
		loveBlosh = 0;
		loveMaki = 0;
		loveKumo = 0;
	}
	public ScenarioProgress(KyaraNum hero){
		this();
		this.hero = hero;
	}
	
	private KyaraNum hero;
	private int day;
	private int loveRoloa;
	private int loveBlosh;
	private int loveMaki;
	private int loveKumo;
	
	public KyaraNum getHero() {
		return hero;
	}
	public void setHero(KyaraNum hero) {
		this.hero = hero;
	}
	public int getDay() {
		return day;
	}
	public void addDay(int day) {
		this.day += day;
	}
	public int getLoveRoloa() {
		return loveRoloa;
	}
	public void addLoveRoloa(int loveRoloa) {
		this.loveRoloa += loveRoloa;
	}
	public int getLoveBlosh() {
		return loveBlosh;
	}
	public void addLoveBlosh(int loveBlosh) {
		this.loveBlosh += loveBlosh;
	}
	public int getLoveMaki() {
		return loveMaki;
	}
	public void addLoveMaki(int loveMaki) {
		this.loveMaki += loveMaki;
	}
	public int getLoveKumo() {
		return loveKumo;
	}
	public void addLoveKumo(int loveKumo) {
		this.loveKumo += loveKumo;
	}
	
	/**
	 * 今の状況をデバッグプリントする
	 */
	public void printState(){
		System.out.println("主人公：" + this.getHeroName());
		System.out.println("ロロア好感度" + loveRoloa);
		System.out.println("ロッシ好感度" + loveBlosh);
		System.out.println("マキ好感度" + loveMaki);
		System.out.println("クモ好感度" + loveKumo);
		
	}
	public String getHeroName() {
		switch(this.getHero()){
		case Roloa:
			return "roloa";
		case Blosh:
			return  "blosh";
		case Maki:
			return "maki";
		case Kumo: 
			return "kumo";
		}
		return null;
	}
	
}
