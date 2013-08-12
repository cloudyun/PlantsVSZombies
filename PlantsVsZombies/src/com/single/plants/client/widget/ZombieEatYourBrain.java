package com.single.plants.client.widget;

import com.framework.client.Framework;
import com.framework.client.mvc.Dispatcher;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.resources.MusicList;
import com.single.plants.client.util.MusicConfig;
/**
 * 僵尸吃掉了你的脑子
 */
public class ZombieEatYourBrain extends Widget implements PVZResources{

	public ZombieEatYourBrain(){
		Element div=DOM.createDiv();
		div.addClassName("ZombieEatYourBrain");
		div.getStyle().setZIndex(Framework.getTopZIndex()+1000);
		setElement(div);
		sinkEvents(Event.ONCLICK);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		Dispatcher.forwardEvent(PVZEvent.PlayMusic, new MusicConfig(MusicList.Scream));
	}
	
	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if(event.getTypeInt()==Event.ONCLICK){
			removeFromParent();
			Dispatcher.forwardEvent(PVZEvent.Back);
		}
	}
}
