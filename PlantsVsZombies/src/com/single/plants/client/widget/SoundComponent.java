package com.single.plants.client.widget;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.framework.client.widget.Component;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.VolumeEvent;
/**
 * 支持声音的组件
 */
public abstract class SoundComponent  extends Component implements Soundable{
	
	private Listener<BaseEvent> enableListener=new Listener<BaseEvent>() {
		public void handleEvent(BaseEvent be) {
			SoundComponent.this.enableSound();
		}
	};
	
	private Listener<BaseEvent> disableListener=new Listener<BaseEvent>() {
		public void handleEvent(BaseEvent be) {
			SoundComponent.this.disableSound();
		}
	};
	
	
	public SoundComponent(){
		this("SoundComponent");
	}
	
	public SoundComponent(String baseStyle){
		super(baseStyle);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		addListener(PVZEvent.EnableSound, enableListener);
		addListener(PVZEvent.DisableSound, disableListener);
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		removeListener(PVZEvent.EnableSound, enableListener);
		removeListener(PVZEvent.DisableSound, disableListener);
	}
}
