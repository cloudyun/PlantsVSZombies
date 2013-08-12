package com.single.plants.client.widget.zombie;

import com.framework.client.event.Listener;
import com.framework.client.util.Rectangle;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.ZombieEvent;
import com.single.plants.client.event.ZombieEvent.Status;
import com.single.plants.client.model.zombie.Zombie;
import com.single.plants.client.widget.Zombier;

public class Zombier1Widget extends Zombier {

	public Zombier1Widget(Zombie zombie){
		super(zombie,zombiesResource.blankZombie());
		offsetY=-30;
		
		addListener(PVZEvent.UpdateZombieStatus, new Listener<ZombieEvent>() {
			public void handleEvent(ZombieEvent be) {
				Status status=be.getStatus();
				switch (status) {
				case Forward:
					img.setSrc(zombiesResource.blankZombie().getURL());
					break;
				case Attack:
					img.setSrc(zombiesResource.zombieAttack().getURL());
				default:
					break;
				}
			}
		});
	}

	


	
	@Override
	public Rectangle attactBody() {
		Rectangle r=el().getBounds(true);
		r.width=1;
		r.y+=r.height/4;
		r.height/=2;
		r.x+=25;
		
		return r;
	}
}
