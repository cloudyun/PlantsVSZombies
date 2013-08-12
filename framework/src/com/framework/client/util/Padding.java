package com.framework.client.util;

public class Padding extends Region{

	

	public Padding(){
		this(0);
	}
	public Padding(int padding){
		this(padding, padding, padding, padding);
	}
	
	public Padding(int top, int right, int bottom, int left) {
		super(top, right, bottom, left);
	}
}
