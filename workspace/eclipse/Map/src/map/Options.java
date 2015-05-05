package map;

import org.kohsuke.args4j.*;  //导入包

public class Options{
	
	
	@Option(name = "-workPath", usage = "Specify path of workspace ")
	public String workPath = "/home/cyno/data/map/";
	
	@Option(name = "-vecFile", usage = "Specify source corpus ")
	public String vecFile = "vectors.txt";
	
	@Option(name = "-datPath", usage = "Specify file of dictionary")
	public String datPath = "topic_distri/";
	
	@Option(name = "-size", usage = "Specify the sum of source lines")
	public Integer size = 200;
	
	@Option(name = "-filterLine", usage = "Specify the least sum of words tagged each line")
	public Integer filterLine = 4;
	
	@Option(name = "-filterLemmaCount", usage = "Specify the least sum of leammas each word")
	public Integer filterLemmaCount = 10;
		
}