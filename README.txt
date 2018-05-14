Technicals Tips for the resolution of the assignment:

0)One Project: MarfeelCradle Developed in a Eclipse Mars.2 (4.5.2); SO Windows 10 and JDK 1.8.0_144

1) Four functionalities (implemented in the es.marfeelcradle.controller.MarfeelCradleController class )

GET /marfeelTopicList ==> Get a list of Topics (texts of <TITLE> tags) used by JSoup class for matching coincidences.
GET /marfeelCradleList ==> Get a list of MarfeelUrlRank stored in the persistence layer
POST /marfeelCradleRank {JSON List of "possible" marfeelable URLs} ==> process a input list of possible marfeelable URLs
POST /marfeelCradleRankAsync {JSON List of "possible" marfeelable URLs} ==> process a input list of possible marfeelable URLS asynchronously 
(using DeferredResult and CompletableFuture)

2)Typical MVC Architecture. Three layers (MarfeelCradleController=>View; JSoupSearchingEngine=>Controller; MarfeelUrlRank/Topic=>Model).
Decoupled Controller layer (JSoupSearchingEngine/IJSoupSearchingEngine) and Persistence layer (MongoDao/IMongoDao) for a better control.

3)For the Persistence Layer, i have used a MongoDB 3.6.4 Community Edition, running at localhost at default port (27017). It's mandatory there is an active
instance of the database for the correct working of the engine. 

Databases/data has been created manually in the MongoDB console. You can create the database with minimum data using the following script.
(src/main/resources/Db.js)

4)You can run the engine using a jetty embedded servlet container (9.3.0.M2). just run a mvn jetty:run instance. (+Profiles -doclint-java8-disable)

5)Apart of TDD classes (JUnit/Mockito/MockMVC) there is a simple Java client. Can be found at src/test/java/es/marfeelcradle/test/marfeelCradleSketch.java

6)URL only are accepted in the form of "[{"url": "http://infigo-cleaner.com"},{"url": "http://louisvuitton.com"},{"url": "http://armani.com"},{"url": "http://anunclas.com"},{"url": "http://canarirural.com"}]"
