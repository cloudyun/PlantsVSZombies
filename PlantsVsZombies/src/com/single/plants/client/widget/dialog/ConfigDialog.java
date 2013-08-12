package com.single.plants.client.widget.dialog;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.EventType;
import com.framework.client.event.Listener;
import com.framework.client.mvc.Dispatcher;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.util.PVZConfig;
import com.single.plants.client.widget.tool.Button;

public class ConfigDialog extends Dialog {
	private Button enableSound=new Button("开启音乐");
	private Button disableSound=new Button("关闭音乐");
	
	public ConfigDialog(){
		addButton(enableSound);
		addButton(disableSound);
	}
	
	@Override
	protected void onLoad() {
		enableSound.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				PVZConfig.get().setSoundable(true);
				Dispatcher.forwardEvent(PVZEvent.EnableSound);
				ConfigDialog.this.hide();
			}
		});
		disableSound.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				PVZConfig.get().setSoundable(false);
				Dispatcher.forwardEvent(PVZEvent.DisableSound);
				ConfigDialog.this.hide();
			}
		});
	}
}
