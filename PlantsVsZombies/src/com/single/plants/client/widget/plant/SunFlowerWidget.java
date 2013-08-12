package com.single.plants.client.widget.plant;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.framework.client.util.Point;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.widget.BattleScreen;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.SunShine;

public class SunFlowerWidget  extends Planter{

	private static int repeat=15000;
	public SunFlowerWidget(Plant p){
		super(p,plantsGif.sunFlower());
		getElement().getStyle().setZIndex(Framework.getTopZIndex());
		addListener(PVZEvent.Pause, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				pause=true;
				if(t!=null){
					t.cancel();
				}
			}
		});
		addListener(PVZEvent.Resume, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				pause=false;
				if(t!=null){
					t.scheduleRepeating(repeat);
				}
			}
		});
	}
	
	private Timer t;
	protected boolean pause;
	private BattleScreen screen;
	@Override
	public void start() {
		if(getParent() instanceof PlayGround){
			screen=(BattleScreen) getParent().getParent();
		}else{
			return;
		}
		if(t==null){
			t=new Timer() {
				@Override
				public void run() {
					Point point=SunFlowerWidget.this.el().getXY();
					double r=Math.random();
					screen.add(new SunShine(point.x+(int)(100*r), point.y+(int)(100*r)));
				}
			};
		}
		
		t.scheduleRepeating(repeat);
	}
	@Override
	public void stop() {
		if(t!=null){
			t.cancel();
			t=null;
		}
	}
	
	@Override
	protected void onDetach() {
		stop();
		super.onDetach();
	}
	
	@Override
	public void fixPosition() {
		Point point=PVZUtil.convertToPiexl(row, column);
		el().setLeft(point.x-gif.getWidth()/2);
		el().setTop(point.y-gif.getHeight()/2-10);
	}
	
}
