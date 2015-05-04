package pairup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Distri{
	
	HashMap<String, Integer> lemmaCount;
	HashMap<String,String[]> distri;
	
	// Path
	String tmFile;
	String wordListFile;
		
	/////////////////////////Methods//////////////////////////
	
	// Constructor
	public Distri(Options option){
		tmFile = option.workPath + option.tmFile;
		wordListFile = option.workPath + option.wordListFile;
		lemmaCount = new HashMap<String, Integer>();
		distri = new HashMap<String,String[]>();
	}
	
	public boolean readWordList(){
		try{
			BufferedReader wordListReader = new BufferedReader(new InputStreamReader(new FileInputStream(wordListFile)));
			String line = wordListReader.readLine();
			while (line != null ){
				String words[] = line.split(" ");
				if (words.length == 2){
					int num = 0;
					try{
						num = Integer.parseInt(words[1]);
					}catch(Exception e){
						line = wordListReader.readLine();
						continue;
					}
					lemmaCount.put(words[0], num);
				}
				line = wordListReader.readLine();
			}
			wordListReader.close();
			System.out.println("Read word list successfully !");
			return true;
		}catch(Exception e){
			System.out.println("Error when read word list!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readTm(){
		if (!readWordList())
			return false;
		
		String head = "";
		int length = 0;
		String temp[] = null;
		
		try{
			BufferedReader tmReader = new BufferedReader(new InputStreamReader(new FileInputStream(tmFile)));
			String line = tmReader.readLine();
			while (line != null){
				
				String words[] = line.split(" ");
				int r = 0;
				try{
					r = Integer.parseInt(words[1].substring(words[1].indexOf('.') + 1));
					//System.out.println(r);
				}catch(Exception e){
					e.printStackTrace();
					line = tmReader.readLine();
					continue;
				}

				if ( r == 1){
					head = words[0];
					length = lemmaCount.get(words[0]);
					temp = new String[length + 1];
				}
				try{
				temp[r] = words[2].substring(0,words[2].indexOf('/'));
				}catch(Exception e){
					line = tmReader.readLine();
					continue;
				}
				
				//System.out.println( words[0] + ' ' + temp[r]); // Debug;
				
				if ( words[0].equals(head) && r == length){
					distri.put(words[0], temp);
				}
				
				
				line = tmReader.readLine();
			}
			tmReader.close();
			
			System.out.println("Read tm successfully ...");
			return true;
		}catch(Exception e){
			System.out.println("Error when bulid tm!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public String get(String word, int r){
		try{
			return distri.get(word)[r];
		}catch(Exception e){
			return null;
		}
	}
}