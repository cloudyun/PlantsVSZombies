package com.single.plants.client.widget.plant;

import java.util.List;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.framework.client.util.Point;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.weapon.PeashooterBulletWeaponer;

public class PeashooterWidget extends Planter {

	public PeashooterWidget(Plant p){
		super(p,plantsGif.peashooter());
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
					t.scheduleRepeating(2000);
				}
			}
		});
	}
	
	private Timer t;
	protected boolean pause;

	@Override
	public void start() {
		if(t==null){
			t=new Timer() {
				@Override
				public void run() {
					PlayGround ground=(PlayGround) PeashooterWidget.this.getParent();
					List<Zombier> zombiers=ground.getZombiers();
					boolean exist=false;
					for(Zombier z:zombiers){
						if(z.row==row&&z.getHp()>=0){
							exist=true;
							break;
						}
					}
					if(!exist){
						return;
					}
					int left=PeashooterWidget.this.el().getLeft(true);
					int top=PeashooterWidget.this.el().getTop(true);
					left+=60;
					top+=5;
					
					ground.addWeapon(new PeashooterBulletWeaponer(left, top));
				}
			};
		}
		t.scheduleRepeating(2000);
	}

	@Override
	public void stop() {
		t.cancel();
	}
	@Override
	public void fixPosition() {
		Point point=PVZUtil.convertToPiexl(row, column);
		el().setLeft(point.x-gif.getWidth()/2);
		el().setTop(point.y-gif.getHeight()/2-10);
	}
	
}
