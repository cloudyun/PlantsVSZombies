package com.single.plants.client.util;
/**
 * PVZ配置
 * 单例
 * @author Administrator
 *
 */
public class PVZConfig {

	private static PVZConfig config=new PVZConfig();
	public static PVZConfig get(){
		return config;
	}
	/**
	 * 是否开启声音
	 */
	private boolean soundable=true;
	
	private PVZConfig(){}

	public boolean isSoundable() {
		return soundable;
	}

	public void setSoundable(boolean soundable) {
		this.soundable = soundable;
	}
	
}
