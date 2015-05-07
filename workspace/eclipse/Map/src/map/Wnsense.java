package map;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Wnsense{
	
	HashSet<String> stops;
	HashMap<String,Double[]> word2vec;
	HashMap<String,Integer[]> index;
	String[] lines;
	Double[][] id2vec;
	int[] flag;
	
	int size;
	int length;
	
	String vecFile;
	String indexFile;
	String dataFile;
	String stopFile;
	/////////////////////Methods//////////////////
	
	// Constructor
	public Wnsense(Options option){
		
		length = 4000000;
		
		vecFile = option.workPath + option.vecFile;
		indexFile = option.workPath + option.indexFile;
		dataFile = option.workPath + option.dataFile;
		stopFile = option.workPath + option.stopFile;
		
		word2vec = new HashMap<String,Double[]>();
		index = new HashMap<String,Integer[]>();
		stops = new HashSet<String>();
		
		id2vec = new Double[length][];
		lines = new String[length];
		
		this.length = option.length;
		flag = new int[length];
		for (int i = 0; i < length; i++)
			flag[i] = 0;
	}

	public Entry<Integer[], Double[][]> getWn(String word) {
		
		Integer[] ids = index.get(word);
		Double[][] wn = new Double[ids.length][];

		for ( int i = 0; i < ids.length; i ++){
			wn[i] = getId(ids[i]);
			//System.out.println(i + "\t:" + wn[i].length);
		}
		
		/*for ( int i = 0; i < ids.length; i ++){
			System.out.print(ids[i]+"\t");
			for (int j = 0; j < wn[i].length; j++){
				System.out.print(wn[i][j] + "\t" );
			}
			System.out.println();
		}*/
		return (new AbstractMap.SimpleEntry<Integer[],Double[][]>(ids,wn));
	}
	
	public Double[] getId( int r){
		if (flag[r] == 1)
			return id2vec[r];
		
		Double[] vec = new Double[size];
		for (int i = 0; i < size; i ++)
			vec[i] = 0.0;
		
		String words[] = lines[r].split(" ");
		try {
			int num = Integer.parseInt(words[3]);
			for (int i = 0; i < num; i++) {
				iterPlus(vec, words[4 + 2 * i]);

				String state = lines[r].substring(lines[r].indexOf('|') + 2);
				words = state.split(" ");
				for (i = 0; i < words.length; i++) {
					iterPlus(vec, clean(words[i]));
				}
			}
			
		id2vec[r] = vec;
		flag[r] = 1;
		//for (int i = 0; i < vec.length; i++)
			//System.out.print(i + "\t");
		System.out.println();
		return vec;
		} catch (Exception e) {
			id2vec[r] = null;
			flag[r] = 1;
			return vec;
		}

	}
	
	public String  clean(String word){
		if (stops.contains(word))
			return null;
		word = word.replace("\""," ").replace(";"," ").trim();
		return word;
	}
	
	public void iterPlus(Double[] Vec, String word){
		if (word == null )
			return ;
		try{
			if (word2vec.containsKey(word)){
				Double[] vec = word2vec.get(word);
				
				for (int i = 0; i < size; i++){
					Vec[i] += vec[i];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean readStop(){
		try{
			BufferedReader stopReader = new BufferedReader(new InputStreamReader(new FileInputStream(stopFile)));
			String line = stopReader.readLine();
			int r = 0;
			while (line != null){
				stops.add(line);
				line = stopReader.readLine();
			}
			
			
			stopReader.close();
			System.out.println("Read stop file successfully!");
			return true;			
		}catch(Exception e){
			System.out.println("Error when read stop file!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readData(){
		try{
			BufferedReader dataReader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile)));
			String line = dataReader.readLine();
			int r = 0;
			while (line != null){

				try{
					int num = Integer.parseInt(line.substring(0, line.indexOf(' ')));
					lines[num] = line;
					//System.out.println(++r + "\t" +num); /////////////////Debug
				}catch(Exception e){
					//System.out.println("Skip :\t" + words[0] + (++rr) );//+ e.getMessage());
					//e.printStackTrace();
					line = dataReader.readLine();
					continue;
				}
				
				line = dataReader.readLine();
			}
			
			
			dataReader.close();
			System.out.println("Read data file successfully!");
			return true;			
		}catch(Exception e){
			System.out.println("Error when read data file!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readIndex(){
		try{
			BufferedReader indexReader = new BufferedReader(new InputStreamReader(new FileInputStream(indexFile)));
			String line = indexReader.readLine();
			int rr = 0;
			while (line != null){
				String words[] = line.split(" ");
				try{
					int num = Integer.parseInt(words[2]);
					Integer[] ids = new Integer[num];
					for ( int i = words.length - num, r = 0; i < words.length;r ++, i++){
						ids[r] = Integer.parseInt(words[i]);
					}
					index.put(words[0], ids);
					//System.out.println(words[0]); /////////////////Debug
				}catch(Exception e){
					//System.out.println("Skip :\t" + words[0] + (++rr) );//+ e.getMessage());
					//e.printStackTrace();
					line = indexReader.readLine();
					continue;
				}
				
				line = indexReader.readLine();
			}
			
			
			indexReader.close();
			System.out.println("Read index file successfully!");
			return true;			
		}catch(Exception e){
			System.out.println("Error when read index file!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
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
	
}