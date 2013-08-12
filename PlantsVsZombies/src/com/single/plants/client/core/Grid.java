package com.single.plants.client.core;

public class Grid {

	public static final Grid EMPTY=new Grid(0,0);
	
	public int row;
	
	public int column;
	
	public Grid(){}
	
	public Grid(int row,int column){
		this.row=row;
		this.column=column;
	}
}
