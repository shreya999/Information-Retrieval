package com.shreya;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ActionServlet
 */
@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		String query="";
		String res = "";
		 String path = getServletContext().getRealPath("/")+"en-pos-maxent.bin";
		query = request.getParameter("query");
			ArrayList<ReturnObject> arr = FinalQueryProcessing.processAllTypeQuery(query, path);
			System.out.println(arr.size());
			
			for(int i=0;i<arr.size();i++)
			{
			ReturnObject ob = arr.get(i);
			String userName=""; //Link to tweet_userURL
			String tweetText="";//Link to tweet_url
			String tweetDate="";
			userName = ob.tweet_userscreenname;
			tweetText = ob.tweet_text;
			tweetDate = ob.tweet_date;
			res = res +
					" <div class=\"row\"" +
					" <div class=\"col s2\">" +
					" 	<div class=\"card teal lighten-2 center-align\"> " + 
					" 		<div class=\"card-content black-text\" id=\"tbox2\" name=\"tbox2\">" + 

					"<a href="+ob.tweet_userUrl+">"+ userName +"</a>" + "<br>" +
					"<a href="+ob.tweet_url+">"+tweetText+"</a>"+ "<br>" + "<p id=\"datetweet\">" +
					tweetDate + "</p><br>"+
					" 			<input onclick=\"responsiveVoice.speak($('#tbox2').val(),'US English Female');\" type=\"button\" value='Listen' /> " + 
					" <div class=\"card-action\"><a href=\"http://www.twitter.com\">LINK TWITTER</a></div></div></div></div></div> " ;
			System.out.println(res);
			}
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
