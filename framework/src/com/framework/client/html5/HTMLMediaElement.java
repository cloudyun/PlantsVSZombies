package com.framework.client.html5;

import com.google.gwt.dom.client.Element;

public class HTMLMediaElement extends Element{

	public static final int NETWORK_EMPTY=0;
	public static final int NETWORK_IDLE=1;
	public static final int NETWORK_LOADING=2;
	public static final int NETWORK_NO_SOURCE=3;

	public static final int HAVE_NOTHING=0;
	public static final int HAVE_METADATA=1;
	public static final int HAVE_CURRENT_DATA=2;
	public static final int HAVE_FUTURE_DATA=3;
	public static final int HAVE_ENOUGH_DATA=4;
	
	protected HTMLMediaElement(){}
	
	public native final void load()/*-{
		this.load();
	}-*/;
	
	public native final void play()/*-{
		this.play();
	}-*/;
	
	public native final void pause()/*-{
		this.pause();
	}-*/;
	/**
	 * 是否在暂停状态
	 */
	public native final boolean isPaused()/*-{
		return this.paused;
	}-*/;
	/**
	 * 是否自动播放
	 */
	public native final boolean isAutoPlay()/*-{
		return this.autoplay;
	}-*/;
	/**
	 * 是否循环播放
	 */
	public native final boolean isLoop()/*-{
		return this.loop;
	}-*/;
	
	public native final int getReadyState()/*-{
		return this.readyState;
	}-*/;
	
	public native final int getNetworkState()/*-{
		return this.networkState;
	}-*/;
}
