package it.univr.GameOfLife;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyGame extends Canvas implements MouseListener,ComponentListener,ChangeListener{

	//OGGETTO PER LA SINCRONIZZAZIONE TRA LE THREAD
	private Object obj = new Object();
	
	//DIMENSIONE DELLA CELLULA
	private int dimension;
	//MATRICE CONTENENTE LE CASELLE DELLO SCHERMO ( true - cellula , false - niente )
	private boolean[][] matriceElementi;
	
	//VARIABILI PER IL DOUBLE BUFFERING PER EVITARE IL BLINK DEL CANVAS
	private Graphics bufferedGraphics;
	private Image image;
	
	//ATTESA , TEMPO DI ATTESA TRA UN AGGIORNAMENTO E L'ALTRO (ms)
	private int attesa;
	
	/**
	 * Costruttore del gioco
	 * @param dimension - dimensione della singola cellula in pixel
	 */
	public MyGame(int dimension,int attesa){
		this.setBackground(Color.white);
		this.attesa = attesa;
		this.dimension = dimension;
		this.addMouseListener(this);
		this.addComponentListener(this);
		new RepaintThread().start();
		new NextStateKingThread(2).start();
	}
	
	/**
	 * Funzione richiamata al ridisegno del Canvas
	 */
	@Override
	public void paint(Graphics g){
		g.drawImage(image,0,0,this);
	}
	
	/**
	 * Update funzione che viene richiamata al ridisegno, prima del Paint
	 * Reimplementata per migliorare il double buffering.
	 */
	@Override
	public void update(Graphics g){
		if(image==null){
			image = createImage(this.getWidth(),this.getHeight());
			bufferedGraphics = image.getGraphics();
		}
		paint(g);
	}

	
	
	//ASCOLTATORI
	@Override
	public void mouseClicked(MouseEvent e) {
		if(matriceElementi==null){
			return;
		}
		try{
			matriceElementi[e.getX()/dimension][e.getY()/dimension] = true;
		}
		catch(ArrayIndexOutOfBoundsException error){
			
		}
		paintMatrix();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		JSlider sli = (JSlider) arg0.getSource();
		attesa = sli.getValue()/100;
	}
	//FINE ASCOLTATORI
	
	
	/**
	 * Disegna le matrici delle cellule nel buffer
	 */
	private void paintMatrix(){
		for(int I=0;I<matriceElementi.length;I++){
			for(int J=0;J<matriceElementi[I].length;J++){
				if(matriceElementi[I][J]){
					bufferedGraphics.setColor(Color.black);
					bufferedGraphics.fillRect(I*dimension,J*dimension,dimension,dimension);
				}
				else{
					bufferedGraphics.setColor(Color.white);
					bufferedGraphics.fillRect(I*dimension,J*dimension,dimension,dimension);
				}
			}
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		image = createImage(this.getWidth(),this.getHeight());
		bufferedGraphics = image.getGraphics();
		
		synchronized(obj){
			boolean[][] temp = matriceElementi;
			matriceElementi = new boolean[this.getWidth()/dimension][this.getHeight()/dimension];
			if(temp!=null){
				int minLung = (  temp[0].length < matriceElementi[0].length ? temp[0].length : matriceElementi[0].length );
				for(int I=0;I< (  temp.length < matriceElementi.length ? temp.length : matriceElementi.length ) ; I++){
					System.arraycopy(temp[I],0, matriceElementi[I], 0, minLung);
				}
			}
		}
		paintMatrix();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//CLASSE PER IL REPAINT DELLA THREAD
	private class RepaintThread extends Thread{
		
		@Override
		public void run(){
			while(true){
				
				//Mi sincronizzo con la thread del calcolo dello stato successivo
				//Non voglio che ci sia un paint mentre si sta calcolando lo stato successivo
				synchronized(obj){
					MyGame.this.repaint();
				}
				
				try{
					sleep(50);
				}catch(InterruptedException e){
					
				}
			}
		}
		
	}
	
	//THREAD CHE SI OCCUPA DI CALCOLARE LO STATO SUCCESSIVO DEL GIOCO ( DELEGA IL CALCOLO A DELLE SOTTO-THREAD )
	private class NextStateKingThread extends Thread{
		
		private int nThread = 2;
		private boolean[][] nextMatrix;
		
		private int colonna; //X - J
	    private int riga;  //Y - I
	    
	    private Object objSyncSlave = new Object();
		
		public NextStateKingThread(int nThread){
			if(nThread!=0){
				this.nThread = nThread;
			}
		}
		
		@Override
		public void run(){
			
			while(true){
				
				if(matriceElementi!=null){
					
					NextStateSlaveThread[] schiavi = new NextStateSlaveThread[nThread];
					for(int I = 0;I<nThread;I++){
						schiavi[I] = new NextStateSlaveThread();
					}
					
					nextMatrix = new boolean[matriceElementi.length][matriceElementi[0].length];
					
					colonna = 0;
					riga = 0;
					
					//Mi sincronizzo con la thread del calcolo dello stato successivo
					//Non voglio che ci sia un paint mentre si sta calcolando lo stato successivo
					synchronized(obj){
						
						for(int I = 0;I<nThread;I++){
							schiavi[I].start();
						}
						
						
						for(int I = 0;I<nThread;I++){
							try {
								schiavi[I].join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						matriceElementi = nextMatrix;
						
						paintMatrix();
						
					}
					
				}
				
				try{
					for(int I=0;I<100;I++){
						sleep(attesa);
					}
				}catch(InterruptedException e){
					
				}
			}
			
			
			
		}
		 
		
		private class NextStateSlaveThread extends Thread{
			
			int riga;
			int colonna;
			
			@Override
			public void run(){
				
				while(true){
					
					synchronized(objSyncSlave){
						riga = NextStateKingThread.this.riga;
						colonna = NextStateKingThread.this.colonna++;
						if(colonna>=nextMatrix[0].length){
							colonna = 0;
							NextStateKingThread.this.colonna = 0;
							NextStateKingThread.this.riga++;
							riga=riga+1;
						}
					}
				
					if(riga>=nextMatrix.length){
						break;
					}
					
					int numero = 0;
					for(int I=riga-1;I<=riga+1;I++){
						for(int J=colonna-1;J<=colonna+1;J++){
							
							if(I<0 || I>=matriceElementi.length)
								continue;
							if(J<0 || J>=matriceElementi[I].length)
								continue;
							
							if(matriceElementi[I][J]){
								numero++;
							}
							
						}
					}
					
					if(matriceElementi[riga][colonna]==true){
						numero = numero -1; //TOLGO ME STESSO CHE SONO ACCESSO
						if(numero > 3 || numero < 2){
							nextMatrix[riga][colonna] = false;
						}
						else{
							nextMatrix[riga][colonna] = true;
						}
					}
					else{
						if(numero == 3){
							nextMatrix[riga][colonna] = true;
						}
					}
					
				}
				
			}
			
		}
		
	}
	
	
	
}
