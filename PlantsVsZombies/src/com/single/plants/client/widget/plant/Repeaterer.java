package com.single.plants.client.widget.plant;

import java.util.List;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.weapon.PeashooterBulletWeaponer;

public class Repeaterer extends Planter{

	public Repeaterer(Plant plant) {
		super(plant, plantsGif.repeater());
	}

	private Timer attackTimer;
	private Timer repeatTimer=new Timer() {
		@Override
		public void run() {
			PlayGround ground=(PlayGround) Repeaterer.this.getParent();
			int left=Repeaterer.this.el().getLeft(true);
			int top=Repeaterer.this.el().getTop(true);
			left+=60;
			top+=5;
			ground.addWeapon(new PeashooterBulletWeaponer(left, top));
		}
	};
	@Override
	public void start() {
		if(attackTimer==null){
			attackTimer=new Timer() {
				@Override
				public void run() {
					PlayGround ground=(PlayGround) Repeaterer.this.getParent();
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
					int left=Repeaterer.this.el().getLeft(true);
					int top=Repeaterer.this.el().getTop(true);
					left+=60;
					top+=5;
					
					ground.addWeapon(new PeashooterBulletWeaponer(left, top));
					repeatTimer.schedule(500);
				}
			};
		}
		attackTimer.scheduleRepeating(2000);
	}
	

	@Override
	public void stop() {
		if(attackTimer!=null){
			attackTimer.cancel();
		}
		if(repeatTimer!=null){
			repeatTimer.cancel();
		}
	}

}
