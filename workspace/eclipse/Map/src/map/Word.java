package map;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;

public class Word{
	
	String word;
	String mapPath;
	
	Double[][] matrix;
	int high;
	int width;
	int size;
	
	
	Double[][] wsi;
	Integer[] senseid;
	Double[][] wn;
	
	////////////////////Methods////////////////////
	
	// Constructor
	public Word(String word, Options option){
		this.word = word;
		this.mapPath = option.workPath + option.mapPath;
		this.size = option.size;
	}
	
	public void setWsi(Double[][] wsi){
		this.wsi = wsi;
		this.high = wsi.length;
		//this.size = wsi[0].length;
	}
	
	public void setWn(Entry<Integer[],Double[][]> wn){
		this.wn = wn.getValue();
		this.senseid = wn.getKey();
		this.width = senseid.length;
	}
	
	public void comDistri(){
		matrix = new Double[high][width];
		for (int i = 0; i < high; i++)
			for (int j = 0; j < width; j++)
				matrix[i][j] = comDistance(i,j);
		
	}
	
	public Double comDistance(int i, int j){
		Double ans = 0.0;
		for (int r = 0; r < size; r++){
			ans += wsi[i][r] * wn[j][r];
		}
		return ans;
	}
	
	public boolean saveMatrix(){

		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapPath + word + ".map")));
			for (int i = 0; i < high; i++){
				writer.write("\ttopic#" + (i + 1));
				for (int j = 0; j < width; j++){
					writer.write(senseid[i] + "|||" + matrix[i][j] + "\t");
				}
				writer.write("\n");
			}
			writer.close();
			System.out.println("Save word : " + word + " successfully !");
			return true;
		}catch(Exception e){
			System.out.println("Error when save matrix! :" + word + "\n" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
}