package map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class Transfer{
	
	Options option;
	String workPath;
	String wordListFile;
	
	int size;
	
	Vector<String> wordList;
	Wnsense wnsense;
	Wsisense wsisense;
	
	//////////////////Methods//////////////////
	
	//Constructor
	public Transfer(Options option){
		this.option = option;
		wordListFile = option.workPath + option.wordListFile;
		
		wordList = new Vector<String>();
		readWordList();
		
		wsisense = new Wsisense(option);
		wsisense.readVec();
		
		wnsense = new Wnsense(option);
		wnsense.readIndex();
		wnsense.readVec();
		wnsense.readData();
		wnsense.readStop();
		
	}
	
	public boolean readWordList(){
		try{
			BufferedReader wordListReader = new BufferedReader( new InputStreamReader(new FileInputStream(wordListFile)));
			String line = wordListReader.readLine();
			while( line != null){
				line = line.substring(0,line.indexOf(' '));
				wordList.add(line);
				//System.out.println(line);
				line = wordListReader.readLine();
			}
			wordListReader.close();
			System.out.println("Read word list successfully!");
			return true;
		}catch(Exception e){
			System.out.println("Error when read word list file" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean transfer(){
		File file = new File(option.workPath + option.mapPath);
		if (!file.isDirectory())
			file.mkdirs();
		
		for (int i = 0; i < wordList.size(); i++){
			String word = wordList.get(i);
			Word one = new Word(word,option);
			
			one.setWsi(wsisense.getWsi(word));
			one.setWn(wnsense.getWn(word));
			one.comDistri();
			one.saveMatrix();		
			
		}
		
		return true;
	}
	
}