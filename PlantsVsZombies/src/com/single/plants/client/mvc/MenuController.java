package com.single.plants.client.mvc;

import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Controller;
import com.framework.client.mvc.Dispatcher;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.widget.MainScreen;
/**
 * 主界面
 *
 */
public class MenuController extends Controller {
	private MainScreen screen;
	public MenuController(){
		screen=new MainScreen();
		RootPanel.get().add(screen);
		
		registerEventTypes(PVZEvent.GameStart,PVZEvent.Back,PVZEvent.EnableSound,PVZEvent.DisableSound);
	}
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==PVZEvent.Back){
			Dispatcher.forwardEvent(PVZEvent.DisableBackgroundSound);
			screen=new MainScreen();
			RootPanel.get().add(screen);
		}
		if(screen==null){
			return;
		}
		if(event.getType()==PVZEvent.GameStart){
				screen.removeFromParent();
				screen=null;
		}else if(event.getType()==PVZEvent.EnableSound){
			screen.enableSound();
		}else if(event.getType()==PVZEvent.DisableSound){
			screen.disableSound();
		}
	}

}
