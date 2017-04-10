package com.shreya;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueryProcessing {
	public static HashMap<String, String> queryTokens = null;
	public static void main(String[] args) throws IOException {
		String queryStr = "How many held for duping businessman in Gujrat";
		/*if(queryStr.contains("How many")) {
			String str = queryStr.replace("How many ", "");
			processHowTypeQuery(str);
		}*/
		//String s = processHowTypeQuery(queryStr);
		System.out.println("Done");
	}
	
	public static ArrayList<ReturnObject> func(String query) {
		ArrayList<ReturnObject> returnData = new ArrayList<ReturnObject>();
		//processing
		ReturnObject obj  =  new ReturnObject();
		obj.tweet_text = " Hello this is yash";
		obj.tweet_username = "Yash";
		obj.tweet_userscreenname = "yashjain284";
		obj.tweet_date = "12/01/2016";
		obj.tweet_userUrl = "https://twitter.com/yashjain284";
		obj.tweet_url = "https://twitter.com/OnlyBabaSehgal/status/806931946559315968";
		if(obj.tweet_text!=null)
			returnData.add(obj);
		for(int i=1;i<5;i++)
		{
			returnData.add(obj);
		}
		return returnData;
	}
	
	public String processHowTypeQuery(String str, String path) throws IOException {
		//http://54.203.237.226:8983/solr/gettingstarted/select?indent=on&q=nouns:businessman%20AND%20verbs:held&wt=json
		//System.out.println(str);
		QueryToken.generateQueryTokens(str, path);
		queryTokens = QueryToken.queryTokens;
		System.out.println(queryTokens.size());
		int count = 0;
		Iterator entries = queryTokens.entrySet().iterator();
		String a="http://54.203.237.226:8983/solr/gettingstarted/select?indent=on&wt=json&q=text_en:"+str;
		a = a.replaceAll(" ", "%20");
		/*while (entries.hasNext()) {
		    Map.Entry entry = (Map.Entry) entries.next();
		    String key = (String) entry.getKey();
		    String value = (String) entry.getValue();
		    if(count != 0){
		    	a = a + "%20AND%20";
		    }
		    a=a+value+":"+key;
		    count++;
		    System.out.println("Key = " + key + ", Value = " + value);	
		}*/
		System.out.println(a);
		URL url = new URL(a);
		System.out.println(url);
		URLConnection conn = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		String returnString="No tweets found";
		while ((inputLine = br.readLine()) != null) {
			if(inputLine.contains("tweet_text")) {
				inputLine = inputLine.substring(23, inputLine.length()-3);
				System.out.println(inputLine);
				returnString = inputLine;
				break;
			}
		}
		br.close();
		return returnString;
	}
}
