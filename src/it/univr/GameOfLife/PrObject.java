package it.univr.GameOfLife;

import java.io.FileInputStream;
import java.io.IOException;

public class PrObject {

	private boolean[][] matrix;
	
	public PrObject(String name) throws IOException{
		FileInputStream is = new FileInputStream("GameOfLife/img/"+name+".k23");
		int lrighe = is.read() - 48;
		int lcolo = is.read() - 48;
		matrix = new boolean[lcolo][lrighe];
		int I=0,J=0;
		int c;
		while( true ){
			c = is.read();
			matrix[J][I] = c==49 ? true : false;
			J++;
			if(J>=lcolo){
				J=0;
				I++;
			}
			if(I>=lrighe){
				break;
			}
		}
		
		is.close();
		
	}
	
	
	public void modify(int x, int y, boolean[][] matriceElementi){
		try{
			for(int I=x;I<x+matrix.length;I++){
				for(int J=y;J<y+matrix[0].length;J++){
					matriceElementi[I][J] = matrix[I-x][J-y];
				}
			}
		}catch(IndexOutOfBoundsException e){
			
		}
	}
	
}
