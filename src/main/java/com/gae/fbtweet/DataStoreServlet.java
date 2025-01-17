package com.gae.fbtweet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


@SuppressWarnings("serial")
public class DataStoreServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
		Entity e=new Entity("tweet");
		e.setProperty("status",req.getParameter("text_content"));
		e.setProperty("user_id", req.getParameter("user_id"));
		e.setProperty("first_name", req.getParameter("first_name"));
		e.setProperty("last_name", req.getParameter("last_name"));
		e.setProperty("picture", req.getParameter("picture"));
		e.setProperty("visited_count", 0);
		Cookie user_id = new Cookie("user_id", req.getParameter("user_id"));	
		Cookie first_name= new Cookie("first_name",req.getParameter("first_name"));
		Cookie last_name=new Cookie("last_name", req.getParameter("last_name"));
		Cookie picture = new Cookie("picture", req.getParameter("picture"));
		resp.addCookie(user_id);
		resp.addCookie(first_name);
		resp.addCookie(last_name);
		resp.addCookie(picture);
		Date date = new Date();
        System.out.println(sdf.format(date));
		e.setProperty("timestamp", sdf.format(date));
		Key id=ds.put(e);		
		StringBuffer sb=new StringBuffer();
		String url = req.getRequestURL().toString();
		String baseURL = url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/";
		sb.append(baseURL+"direct_tweet.jsp?id="+id.getId());
		req.setAttribute("status", sb);
		req.getRequestDispatcher("tweet.jsp").forward(req, resp);
	}
}
