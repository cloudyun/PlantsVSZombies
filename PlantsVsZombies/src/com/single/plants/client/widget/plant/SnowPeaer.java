package com.single.plants.client.widget.plant;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.weapon.PeashooterBulletWeaponer;
import com.single.plants.client.widget.weapon.SnowpeaBulletWeaponer;
/**
 * 寒冰射手
 */
public class SnowPeaer extends Planter implements PVZResources{


	private Timer t;


	public SnowPeaer(Plant plant) {
		super(plant, plantsGif.snowPea());
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
	protected boolean pause;

	@Override
	public void start() {
		if(t==null){
			t=new Timer() {
				@Override
				public void run() {
					PlayGround ground=(PlayGround) SnowPeaer.this.getParent();
					List<Zombier> zombiers=ground.getZombiers();
					boolean exist=false;
					for(Zombier z:zombiers){
						if(z.row==row){
							exist=true;
							break;
						}
					}
					if(!exist){
						return;
					}
					int left=SnowPeaer.this.el().getLeft(true);
					int top=SnowPeaer.this.el().getTop(true);
					left+=60;
					top+=5;
					
					ground.addWeapon(new SnowpeaBulletWeaponer(left, top));
				}
			};
		}
		t.scheduleRepeating(2000);
	}
	

	@Override
	public void stop() {
		if(t!=null){
			t.cancel();
		}
	}

}
