package com.single.plants.client.event;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventType;
import com.single.plants.client.widget.Planter;
/**
 * 
 */
public class PlantEvent extends BaseEvent {

	private Planter planter;

	public PlantEvent(Planter planter){
		super(planter);
		this.planter=planter;
	}
	
	public PlantEvent(EventType type) {
		super(type);
	}

	public Planter getPlanter() {
		return planter;
	}

	public void setPlanter(Planter planter) {
		this.planter = planter;
	}
}
