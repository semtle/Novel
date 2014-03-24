
import java.awt.*;
import java.awt.image.*;



/**
 * 大元のイメージデータを読み込み・保持するクラス
 */
import java.applet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

/**
 * イメージを管理するクラス。
 */
public final class ImageLiblary {
	public static final int IMAGE_NUM = 30;
	private static Image[] images;
	/**
	 * 各イメージの識別番号。
	 */
	public final static int NULL 	 = 0,
		//0番台　システム用
			TITLE = 2,
			MAP = 3,
			PLACE = 5,
			CAUTION = 6,
			//10番台　ちびキャラ
					KUMO = 11,
					BLOSH = 12,
					MAKI = 13,
					ROLOA = 14,
			
			//20番台　プロフィール画像
							KUMOPROF = 21,
							BLOSHPROF = 22,
							MAKIPROF = 23,
							ROLOAPROF = 24,
							CHOOSE = 25,
			
			YOBI = 0;
		

	/**
	 * 使用するイメージを全部ロードする。
	 */
	public static void loadImage(){
		images = new Image[IMAGE_NUM];
		
		for(int i = 0; i < IMAGE_NUM ; i++){
			String filename = "img/"+i+".PNG";
			try {
				InputStream is = new FileInputStream(filename);
				images[i] = ImageIO.read(is);
				is.close();
			} catch (FileNotFoundException e) {
				System.out.println(filename + " is not found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static Image getImage(int number){
		return images[number];
	}
	
}
