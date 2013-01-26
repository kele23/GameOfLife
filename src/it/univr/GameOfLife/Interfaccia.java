package it.univr.GameOfLife;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JSlider;

public class Interfaccia extends JFrame {
	
	private MyGame myGame;
	
	public static void main(String args[]){
		new Interfaccia();
	}
	
	public Interfaccia(){
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setBounds(100,100,600,500);
		
		this.getContentPane().setLayout(new GridBagLayout());
		
		myGame = new MyGame(8,1000);
		
		JSlider slider = new JSlider(100,10000);
		slider.addChangeListener(myGame);
		slider.setValue(1000);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.9;
		c.gridx = 0;
		c.gridy = 0;
		this.getContentPane().add(myGame, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		this.getContentPane().add(slider,c);
		
		this.setVisible(true);
	}
	
}
