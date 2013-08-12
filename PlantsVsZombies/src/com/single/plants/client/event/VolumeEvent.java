package com.single.plants.client.event;

import com.framework.client.event.BaseEvent;
/**
 * 音量事件
 * 发送此事件用以改变音量
 */
public class VolumeEvent extends BaseEvent {

	private int volume;
	
	public VolumeEvent(int volume) {
		super(null);
		this.volume=volume;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
