package com.framework.client.util;

public class Point {

	public int x;
	
	public int y;
	
	public Point(){}
	
	public Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	@Override
	public String toString() {
		return ("x: "+x+",y: "+y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Point){
			Point p=(Point) obj;
			if(x==p.x && y==p.y){
				return true;
			}
			return false;
		}
		return super.equals(obj);
	}
}
