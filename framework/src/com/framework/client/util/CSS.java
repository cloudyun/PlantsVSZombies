package com.framework.client.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * CSS 相关辅助类
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-2
 */
public class CSS {

	/**
	 * 向页面添加外部CSS样式
	 * @param id 唯一标志
	 * @param url 外部CSS的URL
	 */
	public static void addStyleSheet(String id,String url){
		Element link=DOM.createElement("link");
		link.setPropertyString("rel", "stylesheet");
		link.setPropertyString("type", "text/css");
		link.setPropertyString("id", id);
		link.setPropertyString("href", url);
		link.setPropertyString("disable", "");
		
		Element head=XDOM.getHead();
		DOM.appendChild(head, link);
	}
	/**
	 * 移除外部的CSS样式
	 * @param id
	 */
	public static void removeStyleSheet(String id){
		Element el=DOM.getElementById(id);
		if(el!=null){
			Element p=DOM.getParent(el);
			DOM.setElementProperty(p, "disabled", "disabled");
			DOM.removeChild(p, el);
		}
	}
}
