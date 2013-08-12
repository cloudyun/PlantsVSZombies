package com.framework.client.html5;

import com.google.gwt.dom.client.TagName;

@TagName("audio")
public class AudioElement extends HTMLMediaElement {

	protected AudioElement() {
		
	}
	
	public native final void setSrc(String src)/*-{
		this.src=src;
	}-*/;
	/**
	 * 设置自动播放
	 * @param auto 自动播放
	 */
	public native final void setAutoPlay(boolean auto)/*-{
		if(auto){
			this.autoplay='autoplay';
		}else{
			this.autoplay='';
		}
	}-*/;
	
	public native final void setPreload(boolean auto)/*-{
		if(auto){
			this.preload='preload';
		}else{
			this.preload='';
		}
	}-*/;
	/**
	 * 设置循环播放
	 * @param loop
	 */
	public native final void setLoop(boolean loop)/*-{
		if(loop){
			this.loop='loop';
		}else{
			this.loop='';
		}
	}-*/;
	/**
	 * 设置是否显示控件
	 * @param show
	 */
	public native final void setControls(boolean show)/*-{
		if(show){
			this.controls='controls';
		}else{
			this.controls='';
		}
	}-*/;
}
