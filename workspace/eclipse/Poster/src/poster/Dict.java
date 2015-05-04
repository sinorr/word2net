package poster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class Dict{
	
	
	HashMap<String, Integer> lemmaCount;
	HashSet<String> stops;
	String dictFile;
	String wordListFile;
	String lemmaPath;
	String stopsFile;
	Integer filterLemma;
	
	//////////////////////Methods//////////////////////////
	
	// Constructor
	public Dict(Options option){
		

		lemmaCount = new HashMap<String, Integer>();
		stops = new HashSet<String>();
		
		dictFile = option.workPath + option.dictFile;
		filterLemma = option.filterLemmaCount;
		wordListFile = option.workPath + "wordList.dat";
		lemmaPath = option.workPath + "lemmas/";
		stopsFile = option.workPath + option.stopsFile;
	}
	
	// I/O Method
	public boolean readDict(){
		try{
			BufferedReader dictReader = new BufferedReader(new InputStreamReader(new FileInputStream(dictFile)));
			BufferedReader stopsReader = new BufferedReader(new InputStreamReader(new FileInputStream(stopsFile)));
			String line = stopsReader.readLine();
			while (line != null){
				stops.add(line);
				line = stopsReader.readLine();
			}
			stopsReader.close();
			
			line = dictReader.readLine();
			while (line != null){
				int flag = line.indexOf(' ');
				if (flag != -1){
					line = line.substring(0, flag).trim();
					if (line.indexOf('_') == -1 && line.indexOf('-') == -1 && !stops.contains(line) && !line.matches(".*\\d+.*") && line.length() > 0){          // Keep words whit high frequency;
						lemmaCount.put(line, 0);
						//System.out.println(line);   // Debug;
					}
				}
				
				
				line = dictReader.readLine();
			}
			dictReader.close();
			System.out.println("Read Dict successfully!");
			return true;
		}catch(Exception e){
			System.out.println("Error when Read Dict ..." + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean saveList(){
		try{
			BufferedWriter wordListWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wordListFile)));
			
			Iterator iter = lemmaCount.entrySet().iterator();
			while (iter.hasNext()){
				Entry<String, Integer> one = (Entry<String, Integer>) iter.next();
				if (one.getValue() < filterLemma){
					deleteLemma(one.getKey());
				}else{
					wordListWriter.write(one.getKey() + ' ' + one.getValue() + '\n');
				}	
			}
			wordListWriter.close();
			System.out.println("Save List successfully!");
			return true;
		}catch(Exception e){
			System.out.println("Error when save list!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteLemma(String word){
		try{
			File d = new File (lemmaPath + word + ".lemma");
			if (d.isFile()){
				d.delete();
			}
			return true;
		}catch(Exception e){
			System.out.println("Error when delete lemma : " + word + '\t' + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public Entry<String,Integer> contains(String word){
		if (lemmaCount.containsKey(word) ){
			return (new AbstractMap.SimpleEntry<String,Integer>(word,lemmaCount.get(word) ));
		}
		return null;
	}
	
	public void update (String word, int num){
		lemmaCount.put(word, num);
	}
}