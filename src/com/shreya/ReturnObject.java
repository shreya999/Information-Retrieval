package com.shreya;

import java.util.ArrayList;

public class ReturnObject {
	public String id,tweet_username,tweet_userscreenname,tweet_userUrl,tweet_url,tweet_text,tweet_date,text_en;
	public ArrayList<String> mentions;
	public ReturnObject() {
		id=tweet_username=tweet_userscreenname=tweet_userUrl=tweet_url=tweet_text=tweet_date=text_en=null;
		mentions = new ArrayList<String>();
	}
}
