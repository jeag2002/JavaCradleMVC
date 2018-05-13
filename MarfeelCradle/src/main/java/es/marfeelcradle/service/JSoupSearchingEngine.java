package es.marfeelcradle.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;
import es.marfeelcradle.dao.IMongoDao;
import es.marfeelcradle.engine.Engine;


@Service("iJSoupSearchingEngine")
public class JSoupSearchingEngine implements IJSoupSearchingEngine{
	

	@Autowired
	IMongoDao mongoDao;
	
	private final Logger logger = LoggerFactory.getLogger(JSoupSearchingEngine.class);
	
	private static final ExecutorService ex = Executors.newCachedThreadPool();
	
	@Autowired
	public JSoupSearchingEngine(IMongoDao _mongoDao){
		mongoDao = _mongoDao;
	}

	
	/**
	 * Get all topicList
	 */
	public List<Topic> getAllTopicList(){
		logger.info("[JSoupEngineService] -- getAllTopicList");
		return mongoDao.getAllTopics();
	}
	
	/**
	 * Get all marfeelUrlRankList 
	 */
	public List<MarfeelUrlRank> getAllMarfeelUrlRankList(){
		logger.info("[JSoupEngineService] -- getAllMarfeelUrlRankList");
		return mongoDao.getAllMarfeelUrlRank();
	}
	
	/**
	 * Process all URL synchronously
	 */
	
	public List<MarfeelUrlRank> processURLSSync(List<MarfeelUrlRank> lRBean){
		
		try{
			logger.info("[JSoupEngineService] -- processURLSSync");
			
			//ArrayList to Stream
			Stream<MarfeelUrlRank> streamList = lRBean.stream();
			//Create a List of JSoupSearchUrl Task within every input MarfeelUrlRank Data
			List<Engine> tasks = streamList.map(i->new Engine(i)).collect(Collectors.toList());
			//Create/reuse a thread for every JSoupSearchUrlTask. Execute run task (Cradle with JSoup and Store in MongoDB)
			List<CompletableFuture<MarfeelUrlRank>> futures = tasks.stream().map(t -> CompletableFuture.supplyAsync(() -> t.run(), ex)).collect(Collectors.toList());
			//Get the results
			List<MarfeelUrlRank> result = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
			return result;
			
		}catch(Exception e){
			logger.error("Error when processing data {}",e.getMessage());
			return null;
		}
	}
	
	/**
	 * Process all URL asynchronously (Async + Non-Blocking)
	 */
	@Override
	public void processURLSASync(List<MarfeelUrlRank> lRBean, DeferredResult<List<MarfeelUrlRank>> dr) {
		
		try{
			logger.info("[JSoupEngineService] -- processURLSAsyncNonBlocking");
			
			
			CompletableFuture.supplyAsync(
					()->{return processURLSSync(lRBean);},ex).
					thenAccept((List<MarfeelUrlRank> responseWrapper)->{dr.setResult(responseWrapper);});
			
			
			/*
			Stream<MarfeelUrlRank> streamList = lRBean.stream();
			List<JSoupSearchUrl> tasks = streamList.map(i->new JSoupSearchUrl(i)).collect(Collectors.toList());
			tasks.stream().map(t -> CompletableFuture.supplyAsync(() -> t.run(), ex).thenAccept((MarfeelUrlRank responseWrapper)->{dr.setResult(responseWrapper);}));
			*/
			
		}catch(Exception e){
			logger.error("Error when processing data {}",e.getMessage());
		}
		
		
	}
	

	
	
	
	
	
	

}
