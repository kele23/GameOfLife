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
		
		myGame = new MyGame(6,500);
		
		JSlider sliderV = new JSlider(20,1000);
		sliderV.addChangeListener(myGame);
		sliderV.setValue(500);
		
		JSlider sliderG = new JSlider(3,20);
		sliderG.addChangeListener(myGame);
		sliderG.setValue(6);
		
		JPanel panelButtons = new JPanel(new GridLayout(9,2));
		
		
		MyButton buttons[] = new MyButton[MyButton.imgs.length];
		for(int I=0;I<MyButton.imgs.length;I++){
			buttons[I] = new MyButton(I);
			buttons[I].addMouseListener(myGame);
			panelButtons.add(buttons[I]);
		}
		
		JScrollPane scrollPane = new JScrollPane(panelButtons);
		
		this.getContentPane().add(myGame, BorderLayout.CENTER);
		this.getContentPane().add(sliderV, BorderLayout.SOUTH);
		this.getContentPane().add(scrollPane, BorderLayout.EAST);
		this.getContentPane().add(sliderG, BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
}
