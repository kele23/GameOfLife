package it.univr.GameOfLife;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

public class Interfaccia extends JFrame {
	
	private MyGame myGame;
	
	
	
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
		
		JPanel panelButtons = new JPanel(new GridLayout(5,2));
		
		
		MyButton buttons[] = new MyButton[11];
		for(int I=0;I<11;I++){
			buttons[I] = new MyButton(I);
			buttons[I].addMouseListener(myGame);
			panelButtons.add(buttons[I]);
		}
		
		JScrollPane scrollPane = new JScrollPane(panelButtons);
		
		this.getContentPane().add(myGame, BorderLayout.CENTER);
		this.getContentPane().add(slider, BorderLayout.SOUTH);
		this.getContentPane().add(scrollPane, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
}
