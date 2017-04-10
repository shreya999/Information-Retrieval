package com.shreya;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class QueryToken {
	public static HashMap<String, String> queryTokens = null;
	public static void main(String[] args) {
		System.out.println("Done");
	}
	public static void generateQueryTokens(String str, String path) throws IOException {
		System.out.println(str);
		queryTokens = new HashMap<String, String>();
		
		//String path="C:/Users/Shreya/workspace/shreya/en-pos-maxent.bin";
		POSModel model = new POSModelLoader().load(new File(path));
		POSTaggerME tagger = new POSTaggerME(model);
	    @SuppressWarnings("deprecation")
		ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(str));
	    String line;
	    while ((line = lineStream.read()) != null) {
	    	String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
	        String[] tags = tagger.tag(whitespaceTokenizerLine);
	        int count  = 0;
	        while(count<whitespaceTokenizerLine.length) {
	        	count ++;
	        	if(tags[count-1].equals("VB")||tags[count-1].equals("VBP")||tags[count-1].equals("VBN")||tags[count-1].equals("VBZ")||tags[count-1].equals("VBG")||tags[count-1].equals("VBD"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "verbs");
	        	else if(tags[count-1].equals("NN") || tags[count-1].equals("NN") || tags[count-1].equals("NN") || tags[count-1].equals("NN"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "nouns");
	        	else if(tags[count-1].equals("JJ") || tags[count-1].equals("JJR") || tags[count-1].equals("JJS"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "adjectives");
	        	else if(tags[count-1].equals("PRP") || tags[count-1].equals("PRP$") || tags[count-1].equals("WP$") || tags[count-1].equals("WP$"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "adverbs");
	        	else if(tags[count-1].equals("RB") || tags[count-1].equals("RBR") || tags[count-1].equals("RBS"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "pronouns");
	        	else if(tags[count-1].equals("DT") || tags[count-1].equals("WDT"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "determiners");
	        	else if(tags[count-1].equals("UH"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "interjections");
	        	else if(tags[count-1].equals("CC"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "conjunctions");
	        	else if(tags[count-1].equals("CD"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "cardinals");
	        	else if(tags[count-1].equals("MD"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "modalAuxs");
	        	else if(tags[count-1].equals("LS"))
	        		queryTokens.put(whitespaceTokenizerLine[count-1], "ListItemMarkers");
	        }
	    }
	    System.out.println("Returned");
	}
}
