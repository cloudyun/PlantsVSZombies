package com.single.plants.client.util;

import com.allen_sauer.gwt.voices.client.Sound;

/**
 * 音乐配置
 */
public class MusicConfig {
	/**音乐文件url地址*/
	private String url;
	/**是否是背景音乐(会循环播放)*/
	private boolean backgroundMusic=false;
	/**音乐文件格式*/
	private String type=Sound.MIME_TYPE_AUDIO_MPEG;
	
	private int volume=50;
	
	public MusicConfig(String url){
		this.url=url;
	}
	public MusicConfig(String url,boolean backgroundMusic){
		this(url);
		this.backgroundMusic=backgroundMusic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isBackgroundMusic() {
		return backgroundMusic;
	}
	public void setBackgroundMusic(boolean backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
}
