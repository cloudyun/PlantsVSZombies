package com.single.plants.client.event;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventType;
import com.framework.client.mvc.AppEvent;
import com.single.plants.client.model.plant.SunFlower;
import com.single.plants.client.widget.SunShine;

public class ShineEvent extends AppEvent{

	private SunFlower sunFlower;
	
	private SunShine sunShine;
	/** 收集的阳光总数 */
	private int totalShine;
	/** 阳光增减，可以为负 */
	private int change;
	
	public ShineEvent(){
		super(null);
	}
	
	public ShineEvent(EventType type,SunShine sunShine){
		super(type, sunShine);
		this.sunShine=sunShine;
	}
	
	public ShineEvent(EventType type) {
		super(type);
	}

	public SunFlower getSunFlower() {
		return sunFlower;
	}

	public void setSunFlower(SunFlower sunFlower) {
		this.sunFlower = sunFlower;
	}

	public int getTotalShine() {
		return totalShine;
	}

	public void setTotalShine(int totalShine) {
		this.totalShine = totalShine;
	}

	public int getChange() {
		return change;
	}

	public void setChange(int change) {
		this.change = change;
	}

	public SunShine getSunShine() {
		return sunShine;
	}

	public void setSunShine(SunShine sunShine) {
		this.sunShine = sunShine;
	}

}
