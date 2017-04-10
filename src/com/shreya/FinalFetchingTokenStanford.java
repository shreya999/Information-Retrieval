package com.shreya;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class FinalFetchingTokenStanford {
	public static List<String> personName = new ArrayList<>();
	public static List<String> locationName = new ArrayList<>();
	public static List<String> date = new ArrayList<>();

	public static void getLocationPersonDate(String tweetString) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation annotation;
		annotation = new Annotation(tweetString);
		pipeline.annotate(annotation);
		personName = null;personName = new ArrayList<>();
		locationName = null;locationName = new ArrayList<>();
		date = null;date = new ArrayList<>();
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		if (sentences != null && !sentences.isEmpty()) {
			CoreMap sentence = sentences.get(0);
			for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				getPersonLocationDate(token);
			}
		}
	}

	public static void getPersonLocationDate(CoreMap token) {
		if (token.get(NamedEntityTagAnnotation.class).equals("PERSON")) {
			personName.add(token.get(TextAnnotation.class));
		}
		if (token.get(NamedEntityTagAnnotation.class).equals("LOCATION")) {
			locationName.add(token.get(TextAnnotation.class));
		}
		if (token.get(NamedEntityTagAnnotation.class).equals("DATE")) {
			date.add(token.get(TextAnnotation.class));
		}
	}
}
