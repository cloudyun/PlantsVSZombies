package com.single.plants.client.mvc;

import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Controller;

public class ScreenController extends Controller {

	
	public ScreenController(){
		addChild(new MusicController());
		addChild(new MenuController());
		addChild(new StageController());
	}
	@Override
	public void handleEvent(AppEvent event) {
			forwardToChild(event);
	}

}
