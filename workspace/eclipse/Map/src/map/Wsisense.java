package map;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Wsisense{
	
	int size;
	int high;
	HashMap<String,Double[]> word2vec;
	
	String vecFile;
	String datPath;
	
	/////////////////Methods//////////////////
	
	//Constructor
	public Wsisense(Options option){
		size = option.size;
		word2vec = new HashMap<String,Double[]>();
		
		vecFile = option.workPath + option.vecFile;
		datPath = option.workPath + option.datPath;
	}
	
	public boolean readVec(){
		try{
			BufferedReader vecReader = new BufferedReader(new InputStreamReader(new FileInputStream(vecFile)));
			String line = vecReader.readLine();
			try{
				size = Integer.parseInt(line.substring(line.indexOf(' ') + 1));
			}catch(Exception e){
				
			}
			
			int r = 0;
			line = vecReader.readLine();
			while(line != null){
				String words[] = line.split(" ");
				
				if (words.length == size + 1){
					Double [] vec = new Double[size + 1];
					for (int i = 0; i < size; i++){
						vec[i] = Double.parseDouble(words[i+1]);
					}
					word2vec.put(words[0], vec);
					if ( ++r % 100 == 0){
						//System.out.print('|');
						//if ( r % 10000 == 0)
							//System.out.println();
					}
						
				}
				line = vecReader.readLine();
			}
			
			vecReader.close();
			System.out.println("Read word2vec successfully!");
			return true;
		}catch(Exception e){
			System.out.println("Error When Read word2vec!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	public Double[][] getWsi(String word){
		try{
			BufferedReader wordReader = new BufferedReader(new InputStreamReader(new FileInputStream(datPath+word+".dat")));
			int max = 0;
			String line = wordReader.readLine();
			while (line != null){
				if(line.startsWith("topic")){
					int num = Integer.parseInt(line.substring(line.indexOf('#') + 1));
					max = max > num ? max : num;
					//System.out.println(max + "    " + line);
				}			
				line = wordReader.readLine();
			}
			
			wordReader.close();
			wordReader = new BufferedReader(new InputStreamReader(new FileInputStream(datPath+word+".dat")));
			
			Double[][] wsi = new Double[max][size];
			for (int i = 0; i < max; i++)
				for (int j = 0; j < size; j++)
					wsi[i][j] = 0.0;
			line = wordReader.readLine();
			int r = 1;
			while(line != null){
				if(line.startsWith("topic")){
					r = Integer.parseInt(line.substring(line.indexOf('#') + 1));
				}else{
					iterPlus(wsi,r,line);
				}
				line = wordReader.readLine();
			}
			
			wordReader.close();
			
			/*
			for (int i = 0; i < max; i++){
				for (int j = 0; j < size; j++)
					System.out.print(wsi[i][j] + "\t");
				System.out.println();
			}
			*/
				
			return wsi;
		}catch(Exception e){
			System.out.println("Error when get word: " + word + "\t" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public void iterPlus(Double[][] wsi, int r, String line){
		r--;
		line = line.trim();
		//System.out.println(line);
		int flag = line.indexOf("|||");
		String item = line.substring(0, flag);
		String freq = line.substring(flag + 3);
		
		try{
			if (word2vec.containsKey(item)){
				Double[] vec = word2vec.get(item);
				//System.out.println(pairs.length + "   " + pairs[0]);
				for (int i = 0; i < size; i++){
					wsi[r][i] += vec[i] * Double.parseDouble(freq);
					//System.out.println(wsi[r][i]);
				}
			}
		}catch(Exception e){
			System.out.println("Efff" + freq);
			e.printStackTrace();
		}
	}
}