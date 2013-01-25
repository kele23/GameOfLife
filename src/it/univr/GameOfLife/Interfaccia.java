package it.univr.GameOfLife;

import javax.swing.JFrame;

public class Interfaccia extends JFrame {
	
	public static void main(String args[]){
		new Interfaccia();
	}
	
	public Interfaccia(){
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setBounds(100,100,600,500);
		
		this.getContentPane().add(new MyGame(8,1000));
		
		this.setVisible(true);
	}
	
}
