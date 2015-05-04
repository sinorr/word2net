package pairup;

import org.kohsuke.args4j.*;  //导入包

public class Options{
	
	
	@Option(name = "-workPath", usage = "Specify path of workspace ")
	public String workPath = "/home/cyno/data/pairup/";
	
	@Option(name = "-withOrderFile", usage = "Specify corpus ordered ")
	public String withOrderFile = "withOrder.dat";
	
	@Option(name = "-wordListFile", usage = "Specify file of word list")
	public String wordListFile = "wordList.dat";
	
	@Option(name = "-tmFile", usage = "Specify file of word list")
	public String tmFile = "tm_wsi";
			
}