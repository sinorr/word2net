import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;

public class temp{
	
	static String filename = "/home/cyno/data/dict/index.adj";
	
	public static void main(String args[]){
		
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line = reader.readLine();
			int r = 0;
			while (line != null ){
				String[] words = line.split(" ");
				try{
					int tmp = Integer.parseInt(words[2]);
					//System.out.println(++r + ":   " +tmp);
					//count[tmp] += 1;
					//max = max > tmp ? max : tmp;
					//System.out.println(max);
				}catch(Exception e){
					line = reader.readLine();
					System.out.println("Fuck");
					continue;
				}
				
				line = reader.readLine();
			}
			
			reader.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}