package map;

import org.kohsuke.args4j.CmdLineParser;

public class Map{
	public static void main(String[] args){
		Options option = new Options();
		CmdLineParser parser = new CmdLineParser(option);
		
		if (args.length == 0){
			System.out.println("Poster\t[options...]\t[arguments...]");
			parser.printUsage(System.out);
			//return;
		}
		
		Transfer transfer = new Transfer(option);
		transfer.transfer();
	}
}