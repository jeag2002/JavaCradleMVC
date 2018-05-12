package es.marfeelcradle.sketch;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

public class marfeelCradleSketch {

	public static void main(String[] args) throws Exception {
		
		String inputStr = "[{\"url\": \"http://infigo-cleaner.com\",\"rank\": 327319}"+
				",{\"rank\": 14068,\"url\": \"http://louisvuitton.com\"}"+
				",{\"rank\": 18589,\"url\": \"http://armani.com\"}" +
				",{\"url\": \"http://anunclas.com\",\"rank\": 542650}" +
				",{\"url\": \"http://canarirural.com\",\"rank\": 719404}]";
		
		//test GET MARFEELCRADLELIST
		final String uriTest = "http://localhost:8080/marfeelCradleList";
		System.out.println("*****************************************************");
		System.out.println("RequestData GET marfeelCradleList");
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uriTest, String.class);
		System.out.println("responseData GET marfeelCradleList (" + response + ")");
		System.out.println("*****************************************************");
		
		//test GET MARFEELTOPICLIST 
		final String uriTest_1 = "http://localhost:8080/marfeelTopicList";
		System.out.println("*****************************************************");
		System.out.println("RequestData GET marfeelTopicList");
		restTemplate = new RestTemplate();
		response = restTemplate.getForObject(uriTest_1, String.class);
		System.out.println("responseData GET marfeelTopicList (" + response + ")");
		System.out.println("*****************************************************");
		
		//test POST SYNC MARFEELCRADLERANK marfeelCradleRank
		final String uriTest_2 = "http://localhost:8080/marfeelCradleRank";
		System.out.println("*****************************************************");
		System.out.println("RequestData POST SYNC marfeelCradleRank");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(inputStr, headers);
		
		response = restTemplate.postForObject(uriTest_2, request, String.class);
		System.out.println("responseData POST response (" + response + ")");
		System.out.println("*****************************************************");
		
		//test POST ASYNC MARFEELCRADLERANKASYNC marfeelCradleRankAsync
		final String uriTest_3 = "http://localhost:8080/marfeelCradleRankAsync";
		
		System.out.println("*****************************************************");
		System.out.println("RequestData POST ASYNC+NON-BLOCKING marfeelCradleRank");
		AsyncRestTemplate asyncTemplate = new AsyncRestTemplate(); 
		
		HttpHeaders headers_1 = new HttpHeaders();
		headers_1.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request_1 = new HttpEntity<>(inputStr, headers_1);
		
		ListenableFuture<ResponseEntity<String>> data  = asyncTemplate.exchange(uriTest_3, HttpMethod.POST ,request_1, String.class);
	
		ResponseEntity<String> res = data.get();
		if (res != null){
			if (res.getBody() != null){
				System.out.println("responseData POST Async response (" + res.getBody() + ")");
			}
		}
		System.out.println("*****************************************************");

		
		
		
	}

}
