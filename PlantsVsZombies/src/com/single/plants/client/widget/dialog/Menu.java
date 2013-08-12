package com.single.plants.client.widget.dialog;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.widget.Component;
import com.framework.client.widget.ModalPanel;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.util.PVZConfig;
import com.single.plants.client.widget.tool.Button;
/**
 * 系统菜单
 */
public class Menu extends Dialog{
	
	public static class Content extends Component{
		public Content(){
			setElement(DOM.createDiv());
		}
	}

	private Content panel;
	private Button exit=new Button("主菜单");
	private Button back=new Button("返回游戏");
	private Button enableSound=new Button("开启音乐");
	private Button disableSound=new Button("关闭音乐");
	public Menu(){
		panel=new Content();
		content=panel;
		addButton(exit);
		addButton(back);
		addButton(enableSound);
		addButton(disableSound);
		setSize("300px", "300px");
	}
	
	@Override
	protected void onLoad() {
		exit.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				hide();
				Dispatcher.forwardEvent(PVZEvent.Back);
			}
		});
		back.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				hide();
			}
		});
		enableSound.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				PVZConfig.get().setSoundable(true);
				Dispatcher.forwardEvent(PVZEvent.EnableSound);
				hide();
			}
		});
		disableSound.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				PVZConfig.get().setSoundable(false);
				Dispatcher.forwardEvent(PVZEvent.DisableSound);
				hide();
			}
		});
	}
	
	
//	public void show(){
//		RootPanel.get().add(this);
//		
//		modal=ModalPanel.pop();
//		modal.show(this);
		
//	}
//	
//	public void hide(){
//		RootPanel.get().remove(this);
//		ModalPanel.push(modal);
//		modal=null;
//	}
}
