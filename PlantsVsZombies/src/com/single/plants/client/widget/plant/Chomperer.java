package com.single.plants.client.widget.plant;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;

import com.framework.client.util.Rectangle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;

public class Chomperer  extends Planter{

	public Chomperer(Plant plant) {
		super(plant, plantsGif.chomper());
	}

	private Timer attackTimer;
	/** 消化时间 */
	private Timer digestTimer;
	@Override
	public void start() {
		attacking();
	}
	
	private void attacking(){
		img.setSrc(plantsGif.chomper().getURL());
		if(attackTimer==null){
			attackTimer=new Timer() {
				@Override
				public void run() {
					PlayGround ground=(PlayGround) getParent();
					List<Zombier> zombiers=ground.getZombiers();
					List<Zombier> temp=new ArrayList<Zombier>();
					for(Zombier z:zombiers){
						if(z.row==row){
							temp.add(z);
						}
					}
					for(Zombier z:temp){
						if(PVZUtil.collisionCheck(attackRange(), z.defenseBody())){
							z.reduceHP(1000);
							attackTimer.cancel();
							digesting();
						}
					}
				}
			};
		}
		attackTimer.scheduleRepeating(1000);
	}
	
	private void digesting(){
		img.setSrc(plantsGif.chomperDigest().getURL());
		if(digestTimer==null){
			digestTimer=new Timer() {
				@Override
				public void run() {
					attacking();
				}
			};
		}
		digestTimer.schedule(20000);
	}
	
	private Rectangle attackRange(){
		Rectangle r=el().getBounds(true);
		r.x+=100;
		r.y+=r.height/4;
		r.height/=2;
		return r;
	}

	@Override
	public void stop() {
		if(attackTimer!=null){
			attackTimer.cancel();
		}
	}

}
