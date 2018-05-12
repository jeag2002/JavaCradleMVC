package es.marfeelcradle.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond.WhenBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;
import es.marfeelcradle.controller.MarfeelCradleController;
import es.marfeelcradle.dao.MongoDao;
import es.marfeelcradle.service.JSoupSearchingEngine;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoDao.class, JSoupSearchingEngine.class, MarfeelCradleController.class})
@WebAppConfiguration
public class MarfeelCradleControllerTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private MarfeelCradleController mCradleController;
	
	@Mock
	private JSoupSearchingEngine jSoupSearchingEngine;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(mCradleController).build();
	}
	
	@Test
	public void testGetTopicList() throws Exception{
		
		Topic top1 = new Topic();
		top1.setId(0);
		top1.setName("news");
		Topic top2 = new Topic();
		top2.setId(1);
		top2.setName("noticias");
		
		ArrayList<Topic> topics = new ArrayList<Topic>();
		topics.add(top1);
		topics.add(top2);
		
	    when(jSoupSearchingEngine.getAllTopicList()).thenReturn(topics);
	    
	    this.mockMvc.perform(get("/marfeelTopicList"))
	    .andExpect(status().isOk())
	    .andExpect(content().contentType("application/json;charset=UTF-8"))
	    .andExpect(content().string("[{\"id\":0,\"name\":\"news\"},{\"id\":1,\"name\":\"noticias\"}]"));
        
	}
	
	@Test
	public void testGetMarfeelCradleList() throws Exception{
		
		MarfeelUrlRank mUR = new MarfeelUrlRank();
		mUR.setId("0");
		mUR.setUrl("http://www.google.com");
		mUR.setRank(2000L);
		
		MarfeelUrlRank mUR_1 = new MarfeelUrlRank();
		mUR_1.setId("1");
		mUR_1.setUrl("http://www.facebook.com");
		mUR_1.setRank(3000L);
		
		MarfeelUrlRank mUR_2 = new MarfeelUrlRank();
		mUR_2.setId("2");
		mUR_2.setUrl("http://www.yahoo.com");
		mUR_2.setRank(1000L);
		
		ArrayList<MarfeelUrlRank> mURL = new ArrayList<MarfeelUrlRank>();
		mURL.add(mUR);
		mURL.add(mUR_1);
		mURL.add(mUR_2);
		
		when(jSoupSearchingEngine.getAllMarfeelUrlRankList()).thenReturn(mURL);
		
		this.mockMvc.perform(get("/marfeelCradleList"))
		.andExpect(status().isOk())
	    .andExpect(content().contentType("application/json;charset=UTF-8"))
	    .andExpect(content().string("[{\"url\":\"http://www.google.com\",\"rank\":2000,\"id\":\"0\"},{\"url\":\"http://www.facebook.com\",\"rank\":3000,\"id\":\"1\"},{\"url\":\"http://www.yahoo.com\",\"rank\":1000,\"id\":\"2\"}]"));
		
	}
	
	@Test
	public void testPostMarfeelCradleList() throws Exception{
		
		MarfeelUrlRank mUR = new MarfeelUrlRank();
		mUR.setId("0");
		mUR.setUrl("http://www.google.com");
		mUR.setRank(2000L);
		
		ArrayList<MarfeelUrlRank> mURL = new ArrayList<MarfeelUrlRank>();
		mURL.add(mUR);
		
		when(jSoupSearchingEngine.processURLSSync(any(ArrayList.class))).thenReturn(mURL);
		
		String data = "[{\"url\":\"http://www.google.com\",\"rank\":2000,\"id\":\"0\"}]";
		
		this.mockMvc.perform(post("/marfeelCradleRank")
		.accept(MediaType.APPLICATION_JSON_UTF8).content(data.getBytes())
		.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
	    .andExpect(content().contentType("application/json;charset=UTF-8"))
	    .andExpect(content().string("[{\"url\":\"http://www.google.com\",\"rank\":2000,\"id\":\"0\"}]"));
		
	}
	

}
