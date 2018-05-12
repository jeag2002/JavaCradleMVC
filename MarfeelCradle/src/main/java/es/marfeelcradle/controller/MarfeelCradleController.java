package es.marfeelcradle.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import es.marfeelcradle.bean.MarfeelUrlRank;
import es.marfeelcradle.bean.Topic;
import es.marfeelcradle.service.IJSoupSearchingEngine;
import es.marfeelcradle.service.JSoupSearchingEngine;


@RestController
public class MarfeelCradleController {

	private final Logger logger = LoggerFactory.getLogger(MarfeelCradleController.class);
	
	@Autowired
	IJSoupSearchingEngine iJSoupSearchingEngine;
	/**
	 * marfeelCradleRank evaluation (sync response POST)
	 * @param requestWrapper
	 * @return
	 */
	@RequestMapping(value = "/marfeelCradleRank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MarfeelUrlRank>> getMarfeelCradleRank(@RequestBody List<MarfeelUrlRank> requestWrapper) {
		logger.info("[MarfeelCradleController] -- marfeelCradleRank POST");
		List<MarfeelUrlRank> responseMarfeelUrlRank = iJSoupSearchingEngine.processURLSSync(requestWrapper);
		return new ResponseEntity<List<MarfeelUrlRank>>(responseMarfeelUrlRank, HttpStatus.OK);
    }
	/**
	 * marfeelCradleRank evaluation (async+nonblocking response POST)
	 * @param requestWrapper
	 * @return
	 */
	@RequestMapping(value = "/marfeelCradleRankAsync", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<List<MarfeelUrlRank>> getMarfeelCradleRankAsync(@RequestBody List<MarfeelUrlRank> requestWrapper){
		logger.info("[MarfeelCradleControllerAsync] -- marfeelCradleRankAsync POST");
		DeferredResult<List<MarfeelUrlRank>> dr = new DeferredResult<List<MarfeelUrlRank>>();
		iJSoupSearchingEngine.processURLSASync(requestWrapper, dr);
		return dr;
	}
	/**
	 * Get Cradle List 
	 * @return
	 */	
	@RequestMapping(value = "/marfeelCradleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MarfeelUrlRank>> marfeelCradleList() {
		logger.info("[MarfeelCradleController] -- getMarfeelCradleList GET");
		List<MarfeelUrlRank> responseMarfeelUrlRank = iJSoupSearchingEngine.getAllMarfeelUrlRankList();
		return new ResponseEntity<List<MarfeelUrlRank>>(responseMarfeelUrlRank, HttpStatus.OK);
    }
	
	/**
	 * Get Topic List (for HTML <TITLE></TITLE> tags)
	 * @return
	 */	
	@RequestMapping(value = "/marfeelTopicList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Topic>> marfeelTopicList() {
		logger.info("[MarfeelCradleController] -- marfeelTopicList GET");
		List<Topic> responseMarfeelUrlRank = iJSoupSearchingEngine.getAllTopicList();
		return new ResponseEntity<List<Topic>>(responseMarfeelUrlRank, HttpStatus.OK);
    }
	
	

}
