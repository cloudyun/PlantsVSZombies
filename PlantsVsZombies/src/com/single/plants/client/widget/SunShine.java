package com.single.plants.client.widget;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.FxEvent;
import com.framework.client.event.Listener;
import com.framework.client.fx.BaseEffect;
import com.framework.client.fx.FxConfig;
import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.ShineEvent;
import com.single.plants.client.model.PVZResources;

public class SunShine extends Component implements PVZResources{

	private int shinePoint=25;
	
	public SunShine(int x,int y) {
		ImageElement img=DOM.createImg().cast();
		img.setSrc(toolResource.sun().getURL());
		Element div=DOM.createDiv();
		div.getStyle().setPosition(Position.ABSOLUTE);
		div.getStyle().setZIndex(Framework.getTopZIndex()+100);
		div.setClassName("pointer");
		div.appendChild(img);
		setElement(div);
		el().setXY(x, y);
	}
	
	private Timer timer;
	@Override
	protected void onLoad() {
		timer=new Timer() {
			@Override
			public void run() {
				SunShine.this.removeFromParent();
			}
		};
		addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				timer.cancel();
				FxConfig config=new FxConfig(500);
				config.setEffectCompleteListener(new Listener<FxEvent>() {
					@Override
					public void handleEvent(FxEvent be) {
						AppEvent ae=new AppEvent(PVZEvent.OnSunCollectChange, SunShine.this);
						SunShine.this.removeFromParent();
						Dispatcher.forwardEvent(ae);
					}
				});
				BaseEffect.move(SunShine.this.el(), 0, 0, config);
			}
		});
		timer.schedule(5000);
	}
	
	@Override
	protected void onUnload() {
		timer.cancel();
	}

	public int getShinePoint() {
		return shinePoint;
	}
}
