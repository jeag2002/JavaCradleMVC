package es.marfeelcradle.jsoup;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;
import es.marfeelcradle.dao.IMongoDao;
import es.marfeelcradle.dao.MongoDao;

/**
 * Tags  
 */
@Component
public class JSoupSearchUrl{
	
	@Autowired
	IMongoDao mongoDao;
	
	private static final String TAG_HTML = "TITLE";
	
	private final Logger logger = LoggerFactory.getLogger(JSoupSearchUrl.class);
	
	private MarfeelUrlRank URL;
	
	public JSoupSearchUrl(){
		URL = new MarfeelUrlRank();
	}
	
	public JSoupSearchUrl(MarfeelUrlRank _URL){
		URL = _URL;
	}
	
	public JSoupSearchUrl(IMongoDao _mongoDao){
		mongoDao = _mongoDao;
	}
	
	public JSoupSearchUrl(MarfeelUrlRank _URL, IMongoDao _mongoDao){
		URL = _URL;
		mongoDao = _mongoDao;
	}
	
	/**
	 * Get rank for an MarfeelUrlRank	 
	 * @param URL
	 * @return
	 */
	
	public MarfeelUrlRank processJSoupURL(MarfeelUrlRank URL){
		
		try{
			
			logger.info("[JSoupSearchURL Thread-("+Thread.currentThread().hashCode()+")] INI");
			
			//get all available topics
			List<Topic> topics = mongoDao.getAllTopics();
			
		    long score = 0;
			Document doc = Jsoup.connect(URL.getUrl()).get();
			Elements title = doc.select(TAG_HTML);
			
			if (title.size()>0){
				for(Element leaf: title){
					if (topics.contains(new Topic(leaf.text()))){
						score++;
					}
				}
			}
			
			title = null;
			doc = null;
			URL.setRank(score);
			
			logger.info("[JSoupSearchURL Thread-("+Thread.currentThread().hashCode()+")] URL_DATA::(" + URL.toString() + ")");
		}catch(IOException e){
			logger.warn("[JSoupSearchURL Thread-("+Thread.currentThread().hashCode()+")]:: error when connect with {} message{}",URL.toString(),e.getMessage());
			URL.setRank(0);
		}catch(Exception e_1){
			logger.warn("[JSoupSearchURL Thread-("+Thread.currentThread().hashCode()+")]:: general Error (" + e_1.getMessage() + ")");
			URL.setRank(0);
		}
		
		return URL;
	}

}
