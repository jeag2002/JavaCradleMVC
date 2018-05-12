package es.marfeelcradle.bean;

/**
 * Marfeel URL/RANK data
 * @author Usuario
 */

public class MarfeelUrlRank {

	private String url;
	private long rank;
	private String id;
	
	
	public MarfeelUrlRank(){
		this.url = "";
		this.rank = 0;
	}
	
	public MarfeelUrlRank(String _id, String _url, long _rank){
		this.id = _id;
		this.url = _url;
		this.rank = _rank;
	}	
	
	public MarfeelUrlRank(String _url, long _rank){
		this.url = _url;
		this.rank = _rank;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getRank() {
		return rank;
	}
	public void setRank(long rank) {
		this.rank = rank;
	}
	
	public String toString(){
		return "MarfeelUrlRank ".concat("url: ").concat(" ").concat(this.url).concat(" ").concat("rank: ").concat(String.valueOf(this.rank));
	}
	
}
