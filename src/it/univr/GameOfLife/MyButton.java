package it.univr.GameOfLife;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton {

	public static final String[] imgs = {"block.png","boat.png","glider.gif","loaf.png","blinker.gif",
		"beehive.png","beacon.gif","pulsar.gif","toad.gif","lwss.gif","glider-gun.png","acorn.png","diehard.png",
		"infinite1.png","infinite2.png","infinite3.png","fpento.png"};
	//private static final String[] imgs = {"beacon.gif","glider.gif","loaf.png","blinker.gif","block.png"};
	
	private PrObject prObject;
	
	public MyButton(int img){
		super(new ImageIcon("GameOfLife/img/"+imgs[img]));
		try {
			prObject = new PrObject(imgs[img]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PrObject getPrObject(){
		return prObject;
	}
	
}
