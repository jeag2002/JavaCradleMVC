> use mydb
> db.urls.insertMany( [
	{ "id" : "0", "url" : "http://www.google.com", "rank":"2000" }, 
	{ "id" : "1", "url" : "http://www.facebook.com", "rank":"3000" },
	{ "id" : "2", "url" : "http://www.test.com", "rank":"2500" }
] )

> db.urls.find()
>db.topics.insertMany([
    {"id":"0","topic":"noticias"},
    {"id":"1","topic":"news"}
])
>db.topics.find()
