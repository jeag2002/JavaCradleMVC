package es.marfeelcradle.bean;

/**
 * Topic data
 */

public class Topic {

	private int id;
	private String name;
	
	public Topic(){
		id = 0;
		name = "";
	}
	
	public Topic(String _name){
		name = _name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "[id:" +id+ "-name:"+name+"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}	
		Topic other = (Topic) obj;
		if (!other.getName().equalsIgnoreCase(this.name)){
			return false;
		}
		return true;
	}
	

}
