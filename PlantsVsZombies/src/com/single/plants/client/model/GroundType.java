package com.single.plants.client.model;

public enum GroundType {
	Ground("草地",1),Flowerpot("花盆",2),LilyPad("睡莲",4);
	
	private String name;
	private int id;
	private GroundType(String name,int id){
		this.name=name;
		this.id=id;
	}
	public String getName(){
		return this.name;
	}
	
	public int getId(){
		return this.id;
	}
}
