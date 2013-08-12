package com.framework.client.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class XDOM {

	public static native Element getHead() /*-{
		return $doc.getElementsByTagName('head')[0];
	}-*/;

	public static native Element getDocument() /*-{
		return $doc;
	}-*/;

	public static native int getBodyScrollLeft() /*-{
		if(@com.framework.client.Framework::isIE && @com.framework.client.Framework::isStrict){
		  return $doc.documentElement.scrollLeft || $doc.body.scrollLeft || 0;
		} else {
		  return $wnd.pageXOffset || $doc.body.scrollLeft || 0;
		}
	}-*/;

	public static native int getBodyScrollTop() /*-{
		if(@com.framework.client.Framework::isIE && @com.framework.client.Framework::isStrict){
		  return $doc.documentElement.scrollTop || $doc.body.scrollTop || 0;
		} else {
		  return $wnd.pageYOffset || $doc.body.scrollTop || 0;
		}
	}-*/;
	
	public static boolean isOrHasChild(com.google.gwt.dom.client.Element parent,com.google.gwt.dom.client.Element child){
		return DOM.isOrHasChild((Element)parent.cast(), (Element)child.cast());
	}
}
