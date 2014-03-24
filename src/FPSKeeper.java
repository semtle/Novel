
import java.awt.Color;
import java.awt.Graphics;

public class FPSKeeper{
	
	private long frameStartTime;
	private final long requireWaitTime = 16;
	
	private long[] waitTimeAry;
	private int waitTimeIndex;
	
	public FPSKeeper(){
		this.frameStartTime = 0;
		waitTimeAry = new long[100];
	}
	
	public void waitForFPS(){
		//前回のフレームからの経過時間を求める
		long frameUsedTime = System.currentTimeMillis() - this.frameStartTime;
		//目標1Fの値から、経過時間を引いて待ち時間を求める
		long waitTime = this.requireWaitTime - frameUsedTime;
		//System.out.println(waitTimeNano/1_000_000);

		if(waitTime > 0){
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitTimeIndex++;
		if(waitTimeIndex >= this.waitTimeAry.length){
			waitTimeIndex = 0;
		}
		this.waitTimeAry[waitTimeIndex] = waitTime;
		//フレームの開始時間を記録する
		this.frameStartTime = System.currentTimeMillis();
		///System.out.println(frameUsedTime);
	}

	public void paint(Graphics g, int ox, int oy) {
		//負荷ゲージ
		for(int i=0 ; i < waitTimeAry.length ; i++){
			int status = (int)(( (double)waitTimeAry[i] / (double)(requireWaitTime)) * 10);
			g.setColor(Color.green);
			g.drawLine(ox + i,oy, ox + i,oy+status);
		}
		
	}
}
