package com.shreya;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class FinalQueryTokenOpennlp {
	public static List<String> verbs = new ArrayList<>();
    public static List<String> nouns = new ArrayList<>();
    public static List<String> adjectives = new ArrayList<>();

	public static void generateQueryTokens(String str, String path) throws IOException {
		verbs = new ArrayList<>();nouns = new ArrayList<>();adjectives = new ArrayList<>();
		POSModel model = new POSModelLoader().load(new File(path));
	    POSTaggerME tagger = new POSTaggerME(model);
	    ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(str));
	    String line;
	    while ((line = lineStream.read()) != null) {
	    	String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
	        String[] tags = tagger.tag(whitespaceTokenizerLine);
	        int count  = 0;
	        while(count<whitespaceTokenizerLine.length) {
	        	count ++;
	        	if(tags[count-1].equals("VB")||tags[count-1].equals("VBP")||tags[count-1].equals("VBN")||tags[count-1].equals("VBZ")||tags[count-1].equals("VBG")||tags[count-1].equals("VBD"))
	        		verbs.add(whitespaceTokenizerLine[count-1]);
	        	else if(tags[count-1].equals("NN") || tags[count-1].equals("NNS") || tags[count-1].equals("NNP") || tags[count-1].equals("NNPS"))
	        		nouns.add(whitespaceTokenizerLine[count-1]);
	        	else if(tags[count-1].equals("JJ") || tags[count-1].equals("JJR") || tags[count-1].equals("JJS"))
	        		adjectives.add(whitespaceTokenizerLine[count-1]);
	        }
	    }
	}
}
