package com.framework.client.util;

public class Margins extends Region{

	
	public Margins(){
		this(0);
	}
	
	public Margins(int margin){
		this(margin,margin,margin,margin);
	}
	
	public Margins(int top, int right, int bottom, int left) {
		super(top, right, bottom, left);
	}

}
