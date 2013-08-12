package com.framework.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;

public class Framework {
	
	private static Version version;

	// 浏览器类型判断
	public static boolean isIE;
	public static boolean isIE6;
	public static boolean isIE7;
	public static boolean isIE8;
	public static boolean isOpera;
	public static boolean isChrome;
	public static boolean isSafari;

	// 浏览器内核判断
	public static boolean isWebKit;
	public static boolean isGecko;
	// 运行环境判断
	public static boolean isWindows;
	public static boolean isMac;
	public static boolean isLinux;
	public static boolean isAir;
	
	public static boolean isStrict;

	public static boolean initialized = false;
	/** 是否使用https安全连接 */
	public static boolean isSecure;

	public static void init() {
		if (initialized) {
			return;
		}
		String ua=getUserAgent();
		isOpera=ua.indexOf("opera")!=-1;
		isIE=!isOpera && ua.indexOf("msie")!=-1;
		isIE7=!isOpera && ua.indexOf("msie 7")!=-1;
		isIE8=!isOpera && ua.indexOf("msie 8")!=-1;
		isIE6=isIE && !isIE7 && !isIE8;
		
		isChrome=!isIE && ua.indexOf("chrome")!=-1;
		
		isWebKit=ua.indexOf("webkit")!=-1;
		
		isSafari=!isChrome && ua.indexOf("safari")!=-1;
		
		isGecko=!isWebKit && ua.indexOf("gecko")!=-1;
		
		isWindows=(ua.indexOf("windows")!=-1 || ua.indexOf("win32")!=-1);
		isMac=(ua.indexOf("macintosh")!=-1 || ua.indexOf("mac os x")!=-1);
		isAir=ua.indexOf("adobeair")!=-1;
		isLinux=ua.indexOf("linux")!=-1;
		
		
		isSecure=Window.Location.getProtocol().toLowerCase().startsWith("https");
		
		isStrict = Document.get().isCSS1Compat();
		
	}

	public native static String getUserAgent() /*-{
		return $wnd.navigator.userAgent.toLowerCase();
	}-*/;

	public static Version getVersion() {
		if(version==null){
			version=new Version();
		}
		return version;
	}
	
	private static int zIndexId=1000;
	public static int getTopZIndex(){
		return ++zIndexId;
	}
}
