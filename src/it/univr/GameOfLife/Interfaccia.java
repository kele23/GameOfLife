package it.univr.GameOfLife;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

public class Interfaccia extends JFrame {
	
	private MyGame myGame;
	
	private static final String[] imgs = {"block.png","boat.png","glider.gif","loaf.png","blinker.gif",
		"beehive.png","beacon.gif","pulsar.gif","toad.gif","lwss.gif"};
	
	public static void main(String args[]){
		new Interfaccia();
	}
	
	public Interfaccia(){
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setBounds(100,100,800,600);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		myGame = new MyGame(8,1000);
		
		JSlider slider = new JSlider(100,10000);
		slider.addChangeListener(myGame);
		slider.setValue(1000);
		
		JButton buttons[] = new JButton[10];
		
		JPanel panelButtons = new JPanel(new GridLayout(5,2));
		for(int I=0;I<10;I++){
			buttons[I] = new JButton(new ImageIcon("GameOfLife/img/"+imgs[I]));
			panelButtons.add(buttons[I]);
		}
		
		JScrollPane scrollPane = new JScrollPane(panelButtons);
		
		this.getContentPane().add(myGame, BorderLayout.CENTER);
		this.getContentPane().add(slider, BorderLayout.SOUTH);
		this.getContentPane().add(scrollPane, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
}
