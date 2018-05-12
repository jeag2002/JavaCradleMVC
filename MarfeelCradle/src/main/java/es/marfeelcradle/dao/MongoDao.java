package es.marfeelcradle.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;
import es.marfeelcradle.mongo.factory.MongoFactory;

@Service("mongoDao")
@Transactional
public class MongoDao implements IMongoDao{
	
	
	private final Logger logger = LoggerFactory.getLogger(MongoDao.class);
	
	static String db_name = "mydb";
	static String db_urls = "urls";
	static String db_topics = "topics";
	
	
	/**
	 * Get List of Topics
	 */
	@Override
	public List<Topic> getAllTopics(){
		List<Topic> topic_list = new ArrayList<Topic>();
		
		DBCollection coll = MongoFactory.getCollection(db_name, db_topics);

		DBCursor cursor = coll.find();	
		while(cursor.hasNext()) {			
			DBObject dbObject = cursor.next();

			try{
				Topic tp = new Topic();
				tp.setId(Integer.parseInt(dbObject.get("id").toString()));
				tp.setName(dbObject.get("topic").toString());				
				topic_list.add(tp);
			}catch(Exception e){
				logger.error("[MongoDAO] exception (" + e.getMessage() + ")");
			}
		}	
		return topic_list;
	}
	
	/**
	 * Add new Marfeel URL
	 * @param user
	 * @return
	 */

	@Override
	public MarfeelUrlRank addNewMarfeelUrl(MarfeelUrlRank marfeelUrlRank) {
		boolean output = false;
		Random ran = new Random();
		
		logger.info("[MongoDAO] insert marfeelUrlRank (" + marfeelUrlRank.toString() + ")");
		
		try {			
				DBCollection coll = MongoFactory.getCollection(db_name, db_urls);

				BasicDBObject doc = new BasicDBObject();
				String key = String.valueOf(ran.nextInt(100));
				
				marfeelUrlRank.setId(key);
				
				doc.put("id", key); 
				doc.put("url", marfeelUrlRank.getUrl());
				doc.put("rank",String.valueOf(marfeelUrlRank.getRank()));

				coll.insert(doc);
				output = true;
		} catch (Exception e) {
			logger.error("[MongoDAO] exception (" + e.getMessage() + ")");			
		}
		
		return marfeelUrlRank;
		
	}
	
	/**
	 * Get List of MarfeelUrlRank
	 */
	
	@Override
	public List<MarfeelUrlRank> getAllMarfeelUrlRank() {
		
		List<MarfeelUrlRank> marfeel_list = new ArrayList<MarfeelUrlRank>();
		DBCollection coll = MongoFactory.getCollection(db_name, db_urls);

		DBCursor cursor = coll.find();	
		while(cursor.hasNext()) {			
			DBObject dbObject = cursor.next();

			try{
				MarfeelUrlRank mUR = new MarfeelUrlRank();
				mUR.setId(dbObject.get("id").toString());
				mUR.setUrl(dbObject.get("url").toString());
				mUR.setRank(Long.parseLong(dbObject.get("rank").toString()));
				marfeel_list.add(mUR);
			}catch(Exception e){
				logger.error("[MongoDAO] exception (" + e.getMessage() + ")");
			}
		}
		return marfeel_list;
	}
	
}
