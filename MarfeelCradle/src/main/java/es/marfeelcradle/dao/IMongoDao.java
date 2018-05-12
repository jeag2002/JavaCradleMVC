package es.marfeelcradle.dao;

import java.util.List;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;

public interface IMongoDao {
	
	//Get all Marfeelable URL ranks
	public List<MarfeelUrlRank> getAllMarfeelUrlRank();
	
	//Get all Topic
	public List<Topic> getAllTopics();
	
	//Set New Marfeel URL
	public MarfeelUrlRank addNewMarfeelUrl(MarfeelUrlRank marfeelUrlRank);
}
