package com.single.plants.client.core;

public class Coordinate {

	public static final Coordinate EMPTY=new Coordinate(0, 0);
	
	public int x;
	public int y;
	
	public Coordinate(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public Coordinate(){}
}
