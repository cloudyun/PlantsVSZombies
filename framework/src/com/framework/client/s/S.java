package com.framework.client.s;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;

public class S {

	private static __s s=GWT.create(__s.class);
	
	public static String src(){
		return s.s().getURL();
	}
	
	public static ImageResource ir(){
		return s.s();
	}
	
	public static ImageElement img(){
		ImageElement img=DOM.createImg().cast();
		img.setSrc(s.s().getURL());
		return img;
	}
	
	interface __s extends ClientBundle{
		ImageResource s();
	}
}
