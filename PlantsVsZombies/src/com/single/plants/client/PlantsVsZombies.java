package com.single.plants.client;

import com.framework.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.single.plants.client.mvc.ScreenController;

public class PlantsVsZombies implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Element img=DOM.getElementById("loading");
		DOM.removeChild(DOM.getParent(img), img);
		
		
//		RootPanel.get().add(new MainScreen());
		Dispatcher.get().addController(new ScreenController());
//		Dispatcher.forwardEvent(PVZEvent.Start);
	}
}
