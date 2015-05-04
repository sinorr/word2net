package poster;

import org.kohsuke.args4j.*;

public class Poster{
	public static void main(String [] args){
		
		Options option = new Options();
		CmdLineParser parser = new CmdLineParser(option);
		
		if (args.length == 0){
			System.out.println("Poster\t[options...]\t[arguments...]");
			parser.printUsage(System.out);
			//return;
		}
		
		Corpus corpus = new Corpus(option);
		corpus.init();
		
		System.out.println("Processing lemmas starts...");
		corpus.transfer();
	}
}