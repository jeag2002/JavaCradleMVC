package es.marfeelcradle.service;

import java.util.List;

import org.springframework.web.context.request.async.DeferredResult;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;

public interface IJSoupSearchingEngine {
	
	//Get marfeel rank list
	public List<MarfeelUrlRank> getAllMarfeelUrlRankList();
	
	//Get Topic  list
	public List<Topic> getAllTopicList();
	
	//Process List MarfeelUrlRank synchronously
	public List<MarfeelUrlRank> processURLSSync(List<MarfeelUrlRank> lRBean);
	
	//Process List MarfeelUrlRank asynchornously
	public void processURLSASync(List<MarfeelUrlRank> lRBean, DeferredResult<List<MarfeelUrlRank>> dr);
	
	
	
}
