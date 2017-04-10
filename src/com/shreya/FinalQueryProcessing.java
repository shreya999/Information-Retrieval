package com.shreya;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FinalQueryProcessing {
	public static List<String> verbs = new ArrayList<>();
    public static List<String> nouns = new ArrayList<>();
    public static List<String> adjectives = new ArrayList<>();
    public static List<String> personNames=new ArrayList<String>();
    public static List<String> locations=new ArrayList<String>();
    public static String nounString = "",verbString = "",adjectiveString = "",
    		locationString = "", personString = "";
    public static String finalOutputTweet = "";
    public static ArrayList<ReturnObject> uIData = new ArrayList<ReturnObject>();
	public static void main(String[] args) throws IOException {
		String queryStr = "what is the problem with demonitization";
		//processAllTypeQuery(queryStr);
		System.out.println("Done");
	}
	public static ArrayList<ReturnObject> processAllTypeQuery(String str, String path) throws IOException {
		//String originalUrl="http://54.203.237.226:8983/solr/gettingstarted/select?indent=on&fl=id,tweet_username,tweet_userscreenname,tweet_userUrl,tweet_url,tweet_text,tweet_date,text_en&wt=json&q=text_en:"+str;
		String originalUrl="http://35.162.152.7:8983/solr/QuestionAnswer/select?indent=on&fl=id,tweet_username,tweet_userscreenname,tweet_userUrl,tweet_url,tweet_text,tweet_date,text_en&wt=json&q=text_en2:"+str;
		originalUrl = originalUrl.replaceAll(" ", "%20");
		
		FinalFetchingTokenStanford.getLocationPersonDate(str);
		FinalQueryTokenOpennlp.generateQueryTokens(str, path);
		
		finalOutputTweet = "";uIData = new ArrayList<ReturnObject>();
		verbs = FinalQueryTokenOpennlp.verbs; verbString = generateVerbString();
		adjectives = FinalQueryTokenOpennlp.adjectives;adjectiveString = generateAdjectiveString();
		nouns = FinalQueryTokenOpennlp.nouns;nounString = generateNounString();
		personNames = FinalFetchingTokenStanford.personName;personString = generateNamesString();
		locations = FinalFetchingTokenStanford.locationName;locationString = generateLocationString();
		String fqString = ""; boolean flag = false;
		String finalUrl = originalUrl;
		//for personnames
		if(personString != null) {
			fqString = "("+nounString+" AND "+adjectiveString+" AND "+verbString+" AND "+locationString+" AND "+personString+")";
			fqString = getNormalizedString(fqString);
			System.out.println("1 "+fqString);
			flag = isOutput(finalUrl+"&fq="+fqString);
			if(flag) return uIData;
			if(!flag) {
				fqString = "((("+nounString+" AND "+adjectiveString+" AND "+verbString+") OR "+locationString+") AND "+personString+")";
				fqString = getNormalizedString(fqString);
				System.out.println("2 "+fqString);
				flag = isOutput(finalUrl+"&fq="+fqString);
				if(flag) return uIData;
				if(!flag) {
					fqString = "((("+nounString+" AND "+verbString+") OR "+adjectiveString+" OR "+locationString+") AND "+personString+")";
					fqString = getNormalizedString(fqString);
					System.out.println("3 "+fqString);
					flag = isOutput(finalUrl+"&fq="+fqString);
					if(flag) return uIData;
					if(!flag) {
						fqString = "(("+nounString+" OR "+verbString+" OR "+adjectiveString+" OR "+locationString+") AND "+personString+")";
						fqString = getNormalizedString(fqString);
						System.out.println("4 "+fqString);
						flag = isOutput(finalUrl+"&fq="+fqString);
						if(flag) return uIData;
					}
				}
			}
		} /*else */if(locationString != null) {
			fqString = "("+nounString+" AND "+adjectiveString+" AND "+verbString+" AND "+locationString+")";
			fqString = getNormalizedString(fqString);
			System.out.println("5 "+fqString);
			flag = isOutput(finalUrl+"&fq="+fqString);
			if(flag) return uIData;
			if(!flag) {
				fqString = "((("+nounString+" AND "+verbString+") OR "+adjectiveString+") AND "+locationString+")";
				fqString = getNormalizedString(fqString);
				System.out.println("6 "+fqString);
				flag = isOutput(finalUrl+"&fq="+fqString);
				if(flag) return uIData;
				if(!flag) {
					fqString = "(("+nounString+" OR "+verbString+" OR "+adjectiveString+") AND "+locationString+")";
					fqString = getNormalizedString(fqString);
					System.out.println("7 "+fqString);
					flag = isOutput(finalUrl+"&fq="+fqString);
					if(flag) return uIData;
				}
			}
		}/* else*/ if(verbString != null) {
			fqString = "("+nounString+" AND "+adjectiveString+" AND "+verbString+")";
			fqString = getNormalizedString(fqString);
			System.out.println("8 "+fqString);
			flag = isOutput(finalUrl+"&fq="+fqString);
			if(flag) return uIData;
			if(!flag) {
				fqString = "(("+nounString+" OR "+adjectiveString+") AND "+verbString+")";
				fqString = getNormalizedString(fqString);
				System.out.println("9 "+fqString);
				flag = isOutput(finalUrl+"&fq="+fqString);
				if(flag) return uIData;
			}
		}/* else*/ if(adjectiveString != null) {
			fqString = "("+nounString+" AND "+verbString+" AND "+adjectiveString+")";
			fqString = getNormalizedString(fqString);
			System.out.println("10 "+fqString);
			flag = isOutput(finalUrl+"&fq="+fqString);
			if(flag) return uIData;
			if(!flag) {
				fqString = "(("+nounString+" OR "+verbString+") AND "+adjectiveString+")";
				fqString = getNormalizedString(fqString);
				System.out.println("11 "+fqString);
				flag = isOutput(finalUrl+"&fq="+fqString);
				if(flag) return uIData;
			}
		}/* else*/ if(nounString != null) {
			fqString = "("+adjectiveString+" AND "+verbString+" AND "+nounString+")";
			fqString = getNormalizedString(fqString);
			System.out.println("12 "+fqString);
			flag = isOutput(finalUrl+"&fq="+fqString);
			if(flag) return uIData;
			if(!flag) {
				fqString = "(("+adjectiveString+" OR "+verbString+") AND "+nounString+")";
				fqString = getNormalizedString(fqString);
				System.out.println("13 "+fqString);
				flag = isOutput(finalUrl+"&fq="+fqString);
				if(flag) return uIData;
			}
		}
		flag = isOutput(finalUrl);
		if(flag) return uIData;
		return uIData;
	}
	
	public static String getNormalizedString(String str) {
		String returnString = str;
		returnString = returnString.replace(" AND null", "");
		returnString = returnString.replace(" OR null", "");
		returnString = returnString.replace("null AND ", "");
		returnString = returnString.replace("null OR ", "");
		returnString = returnString.replace("null", "");
		returnString = returnString.replace("() OR ", "");
		returnString = returnString.replace("() AND ", "");
		returnString = returnString.replace("(())", "");
		returnString = returnString.replace("()", "");
		returnString = returnString.replace(" ", "%20");
		return returnString;
	}
	
	/*public static boolean isOutput(String originalUrl) throws IOException {
		System.out.println(originalUrl);
		URL url = new URL(originalUrl);
		System.out.println(url);
		URLConnection conn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			if(inputLine.contains("tweet_text")) {
				//inputLine = inputLine.replace("","");
				System.out.println(inputLine);
				finalOutputTweet = inputLine;
				return true;
			}
		}
		br.close();
		return false;
	}*/
	
	public static boolean isOutput(String originalUrl) throws IOException {
		System.out.println(originalUrl);
		//URL url = new URL("http://54.203.237.226:8983/solr/gettingstarted/select?fl=id,tweet_username,tweet_userscreenname,tweet_userUrl,tweet_url,tweet_text,tweet_date,text_en,mentions&indent=on&q=text_en:What%20is%20modi&wt=json");
		URL url = new URL(originalUrl);
		URLConnection conn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		ReturnObject returnedObject = new ReturnObject();
		while ((inputLine = br.readLine()) != null) {
			if(inputLine.contains("\"id\"") ){
				uIData.add(returnedObject);
			    returnedObject = new ReturnObject();
			    inputLine = br.readLine();
			}
			if(inputLine.contains("\"tweet_username\"") ) {
				returnedObject.tweet_username = inputLine.substring(inputLine.indexOf("[")+1,inputLine.indexOf("]"));
				inputLine = br.readLine();
			}
			 if(inputLine.contains("\"tweet_userscreenname\"") ) {
				returnedObject.tweet_userscreenname = inputLine.substring(inputLine.indexOf("[")+1,inputLine.indexOf("]"));
				inputLine = br.readLine(); 
			 }
			 if(inputLine.contains("\"tweet_userUrl\"") ) {
					returnedObject.tweet_userUrl = inputLine.substring(inputLine.indexOf("[")+2,inputLine.indexOf("]")-1);
					inputLine = br.readLine();
			 }
			 if(inputLine.contains("\"tweet_url\"") ) {
				returnedObject.tweet_url = inputLine.substring(inputLine.indexOf("[")+2,inputLine.indexOf("]")-1);
				inputLine = br.readLine();
			}
			 if(inputLine.contains("\"tweet_text\"") ) {
				returnedObject.tweet_text = inputLine.substring(inputLine.indexOf("[")+1,inputLine.indexOf("]"));
				inputLine = br.readLine();
			 }
			 if(inputLine.contains("\"tweet_date\"") ) {
				returnedObject.tweet_date = inputLine.substring(inputLine.indexOf("[")+1,inputLine.indexOf("]"));
				inputLine = br.readLine();
			}
			 if(inputLine.contains("\"text_en\"") ) {
				returnedObject.text_en = inputLine.substring(inputLine.indexOf("[")+1,inputLine.indexOf("]"));
				inputLine = br.readLine();
			 }
		}
		br.close();
		for(int i = 0;i<uIData.size();i++) {
			if (uIData.get(i).tweet_username==null)
				uIData.remove(i);	
		}
		if(uIData.size()==0){
			return false;
		}
		else
			return true;
	}
	
	public static String generateNounString() {
		if(nouns.isEmpty())
			return null;
		int count=0;
		String result = "(nouns2:";
		for(String s:nouns) {
			if(count != 0)
				result = result + " OR " + s;
			else
				result = result + s;
			count++;
		}
		result = result+")";
		return result;
	}
	public static String generateVerbString() {
		if(verbs.isEmpty())
			return null;
		int count=0;
		String result = "(verbs2:";
		for(String s:verbs) {
			if(count != 0)
				result = result + " OR " + s;
			else
				result = result + s;
			count++;
		}
		result = result+")";
		return result;
	}
	public static String generateAdjectiveString() {
		if(adjectives.isEmpty())
			return null;
		int count=0;
		String result = "(adjectives2:";
		for(String s:adjectives) {
			if(count != 0)
				result = result + " OR " + s;
			else
				result = result + s;
			count++;
		}
		result = result+")";
		return result;
	}
	public static String generateNamesString() {
		if(personNames.isEmpty())
			return null;
		int count=0;
		String result = "(PersonNames:";
		for(String s:personNames) {
			if(count != 0)
				result = result + " OR " + s;
			else
				result = result + s;
			count++;
		}
		result = result+")";
		return result;
	}
	public static String generateLocationString() {
		if(locations.isEmpty())
			return null;
		int count=0;
		String result = "(Location:";
		for(String s:locations) {
			if(count != 0)
				result = result + " OR " + s;
			else
				result = result + s;
			count++;
		}
		result = result+")";
		return result;
	}
}
