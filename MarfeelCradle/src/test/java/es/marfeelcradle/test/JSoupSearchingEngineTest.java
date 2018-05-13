package es.marfeelcradle.test;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;
import es.marfeelcradle.dao.IMongoDao;
import es.marfeelcradle.service.JSoupSearchingEngine;

@RunWith(MockitoJUnitRunner.class)
public class JSoupSearchingEngineTest {
	
	@Mock
	private IMongoDao mongoDao;
	
	
	@InjectMocks
	private JSoupSearchingEngine iJSoupSearchingEngine;


	@Before
	public void createJSoupSearchEngine(){
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void getListTopicsTest(){
	
		Topic top1 = new Topic();
		top1.setId(0);
		top1.setName("news");
		Topic top2 = new Topic();
		top2.setId(1);
		top2.setName("noticias");
		
		ArrayList<Topic> topics = new ArrayList<Topic>();
		topics.add(top1);
		topics.add(top2);
		
		when(mongoDao.getAllTopics()).thenReturn(topics);
		List<Topic> topics_1 = iJSoupSearchingEngine.getAllTopicList();
		
		assertEquals("Topics Size",topics_1.size(),2);
		
		assertEquals("Topic id -0",0,topics_1.get(0).getId());
		assertEquals("Topic name -0","news",topics_1.get(0).getName());
		
		assertEquals("Topic id -1",1,topics_1.get(1).getId());
		assertEquals("Topic name -1","noticias",topics_1.get(1).getName());
	}
	
	@Test
	public void getAllMarfeelUrlRankListTest(){
		
		MarfeelUrlRank mUR = new MarfeelUrlRank();
		mUR.setId("0");
		mUR.setUrl("http://www.google.com");
		mUR.setRank(2000L);
		
		ArrayList<MarfeelUrlRank> mURL = new ArrayList<MarfeelUrlRank>();
		mURL.add(mUR);
		
		when(mongoDao.getAllMarfeelUrlRank()).thenReturn(mURL);
		List<MarfeelUrlRank> mURL_1 = iJSoupSearchingEngine.getAllMarfeelUrlRankList();
		
		assertEquals("MarfeelUrlRank Size",mURL_1.size(),1);
		
		assertEquals("MarfeelUrlRank id -0","0",mURL_1.get(0).getId());
		assertEquals("MarfeelUrlRank url -0","http://www.google.com",mURL_1.get(0).getUrl());
		assertEquals("MarfeelUrlRank rank -0",2000L,mURL_1.get(0).getRank());
		
		
	}
	

}
