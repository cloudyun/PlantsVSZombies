package com.single.plants.client.widget.plant;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.event.Listener;
import com.framework.client.html5.AudioElement;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.util.Point;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.contant.PVZContant;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.PlantEvent;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.resources.MusicList;
import com.single.plants.client.util.MusicConfig;
import com.single.plants.client.util.PVZConfig;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;

public class PotatoMiner extends Planter{

	public PotatoMiner(Plant plant) {
		super(plant, plantsGif.potatoMineNotReady());
		hp=2;
	}
	private Timer readyTimer;
	private boolean ready=false;
	@Override
	public void start() {
		if(readyTimer==null){
			readyTimer=new Timer() {
				@Override
				public void run() {
					ready();
				}
			};
		}
		readyTimer.schedule(5000);
	}
	
	private void ready(){
		ready=true;
		img.setSrc(plantsGif.potatoMine().getURL());
		fixPosition();
		addListener(PVZEvent.OnHpChange, new Listener<PlantEvent>() {
			public void handleEvent(PlantEvent be) {
				PlayGround ground=(PlayGround) getParent();
				List<Zombier> zombiers=ground.getZombiers();
				List<Zombier> haha=new ArrayList<Zombier>();
				for(Zombier z:zombiers){
					if(PVZUtil.collisionCheck(el().getBounds(true), z.defenseBody())){
						haha.add(z);
					}
				}
				if(haha.size()>0){
					for(Zombier z:haha){
						z.reduceHP(PVZContant.BOOM_POWER);
					}
					stop();
					if(PVZConfig.get().isSoundable()){
						MusicConfig config=new MusicConfig(MusicList.PotatoMine);
						Dispatcher.forwardEvent(PVZEvent.PlayMusic, config);
					}
					img.setSrc(plantsGif.potatoMineBombed().getURL());
					new Timer() {
						public void run() {
							PotatoMiner.this.removeFromParent();
						}
					}.schedule(2000);
					
				}
			}
		});
	}
	
	@Override
	public void fixPosition() {
		if(!ready){
			super.fixPosition();
		}else{
			Point point=PVZUtil.convertToPiexl(row, column);
			el().setLeft(point.x-38);
			el().setTop(point.y-28);
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
