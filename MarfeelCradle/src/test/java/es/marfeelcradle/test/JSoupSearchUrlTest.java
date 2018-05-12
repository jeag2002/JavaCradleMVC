package es.marfeelcradle.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;
import es.marfeelcradle.dao.IMongoDao;
import es.marfeelcradle.dao.MongoDao;
import es.marfeelcradle.jsoup.JSoupSearchUrl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JSoupSearchUrl.class,MongoDao.class})
@WebAppConfiguration
public class JSoupSearchUrlTest {
	
	
	@Mock
	private IMongoDao mongoDao;
    private JSoupSearchUrl jSSU;
	
	@Before
	public void createJSoupSearch(){
		mongoDao = mock(MongoDao.class);
		
	}
	
	@Test
	public void testJSoupSearch_1_KO(){
		
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
		
		
		MarfeelUrlRank mUR = new MarfeelUrlRank();
		mUR.setUrl("**********");
		mUR.setRank(0);
		mUR.setId("x");
		
		jSSU = new JSoupSearchUrl(mongoDao);
		mUR = jSSU.processJSoupURL(mUR);
		assertEquals("Rank default URL", 0L, mUR.getRank());
	}
	
	@Test
	public void testJsoupSearch_2_KO(){
		
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
		
		
		MarfeelUrlRank mUR = new MarfeelUrlRank();
		mUR.setUrl("google.com");
		mUR.setRank(0);
		mUR.setId("x");
		
		jSSU = new JSoupSearchUrl(mongoDao);
		mUR = jSSU.processJSoupURL(mUR);
		assertEquals("Rank google.com URL", 0L, mUR.getRank());
	}
	
	@Test
	public void testJsoupSearch_3_KO(){
		
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
		
		
		MarfeelUrlRank mUR = new MarfeelUrlRank();
		mUR.setUrl("http://google.com");
		mUR.setRank(0);
		mUR.setId("x");
		
		jSSU = new JSoupSearchUrl(mongoDao);
		mUR = jSSU.processJSoupURL(mUR);
		assertEquals("Rank http://google.com URL", 0L, mUR.getRank());
	}
	
	@Test
	public void testJsoupSearch_4_OK(){
		
		Topic top_3 = new Topic();
		top_3.setId(0);
		top_3.setName("LOUIS VUITTON | Select Your Country");
		Topic top_4 = new Topic();
		top_4.setId(1);
		top_4.setName("Fermer Cookie banner");
		
		ArrayList<Topic> topics = new ArrayList<Topic>();
		topics.add(top_3);
		topics.add(top_4);
		
		when(mongoDao.getAllTopics()).thenReturn(topics);
		
		MarfeelUrlRank mUR = new MarfeelUrlRank();
		
		mUR.setUrl("http://louisvuitton.com");
		mUR.setRank(0);
		mUR.setId("x");
		
		jSSU = new JSoupSearchUrl(mongoDao);
		mUR = jSSU.processJSoupURL(mUR);
		assertEquals("Rank http://google.com URL", 2L, mUR.getRank());
	}
}
