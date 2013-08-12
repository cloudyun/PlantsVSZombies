package com.single.plants.client.widget.plant;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.util.Rectangle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.contant.PVZContant;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;

public class CherryBomber  extends Planter{

	public CherryBomber(Plant plant) {
		super(plant, plantsGif.cherryBomb());
		hp=1000;
	}

	@Override
	public void start() {
		new Timer() {
			@Override
			public void run() {
				PlayGround ground=(PlayGround) getParent();
				List<Zombier> zombiers=ground.getZombiers();
				List<Zombier> haha=new ArrayList<Zombier>();
				for(Zombier z:zombiers){
					boolean collision=PVZUtil.collisionCheck(attackRange(), z.defenseBody());
					if(collision){
						haha.add(z);
					}
				}
				for(Zombier z:haha){
					z.reduceHP(PVZContant.BOOM_POWER);
				}
				CherryBomber.this.removeFromParent();
			}
		}.schedule(1000);
	}
	/**
	 * 攻击范围
	 * @return
	 */
	private Rectangle attackRange(){
		Rectangle r=el().getBounds(true);
		r.x-=50;
		r.y-=50;
		r.width+=100;
		r.height+=100;
		return r;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
