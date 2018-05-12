package es.marfeelcradle.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.dao.MongoDao;
import es.marfeelcradle.jsoup.JSoupSearchUrl;

public class Engine {
	
	private MarfeelUrlRank URL;
	private JSoupSearchUrl JSSUrl;
	private MongoDao mDao;
	
	private final Logger logger = LoggerFactory.getLogger(Engine.class);
	
	public Engine(MarfeelUrlRank _URL){
		URL = _URL;
		JSSUrl = new JSoupSearchUrl();
		mDao = new MongoDao();
	}
	
	public MarfeelUrlRank run(){
		MarfeelUrlRank URLP = JSSUrl.processJSoupURL(URL);
		URLP = mDao.addNewMarfeelUrl(URLP);
		logger.info("[Engine Thread-("+Thread.currentThread().hashCode()+")] data (" + URL.toString() + ") inserted into database");
		return URLP;
	}
	

}
