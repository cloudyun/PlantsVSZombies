package com.single.plants.client.widget;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.mvc.Dispatcher;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.widget.dialog.Menu;
import com.single.plants.client.widget.tool.Button;

public class MenuButton extends Button {

	
	public MenuButton(){
		super("菜单");
		getElement().getStyle().setPosition(Position.ABSOLUTE);
		getElement().getStyle().setLeft(680, Unit.PX);
		getElement().getStyle().setTop(0, Unit.PX);
		el().setWidth(120);
		
		addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				Menu menu=new Menu();
				menu.addListener(PVZEvent.OnHide, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						Dispatcher.forwardEvent(PVZEvent.Resume);
					}
				});
				Dispatcher.forwardEvent(PVZEvent.Pause);
				menu.show();
			}
		});
	}
	
//	private Dialog createMenuDialog(){
//		Dialog dialog=new Dialog(DialogType.TwoButton);
//		dialog.setButtonsText("主菜单", "返回游戏", null, null);
//		dialog.addButton1Listener(new Listener<DomEvent>() {
//			@Override
//			public void handleEvent(DomEvent be) {
//				//TODO
//			}
//		});
//		dialog.addButton2Listener(new Listener<DomEvent>() {
//			@Override
//			public void handleEvent(DomEvent be) {
//				Dialog d=(Dialog) be.getSource();
//				d.removeFromParent();
//			}
//		});
//		
//		return dialog;
//	}
}
