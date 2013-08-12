package com.single.plants.client.widget;

import com.framework.client.event.BaseEvent;
import com.framework.client.widget.Component;
import com.single.plants.client.contant.PVZContant;
import com.single.plants.client.core.Pauseable;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.PVZResources;

public class Weaponer extends Component implements PVZResources , Pauseable{

	protected int power=PVZContant.BULLET_POWER;
	
	@Override
	public void pause() {
		fireEvent(PVZEvent.Pause, new BaseEvent(PVZEvent.Pause));
	}

	@Override
	public void resume() {
		fireEvent(PVZEvent.Resume, new BaseEvent(PVZEvent.Resume));
	}
}
