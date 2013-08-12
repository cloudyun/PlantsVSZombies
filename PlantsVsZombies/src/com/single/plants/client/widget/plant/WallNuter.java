package com.single.plants.client.widget.plant;

import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.widget.Planter;

public class WallNuter extends Planter {

	public WallNuter(Plant plant) {
		super(plant, plantsGif.wallNut());
		hp=72;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public boolean reduceHp(int power) {
		if(hp>=48&&hp-power<48){
			img.setSrc(plantsGif.wallNutCracked1().getURL());
		}else if(hp>=25&&hp-power<25){
			img.setSrc(plantsGif.wallNutCracked2().getURL());
		}
		return super.reduceHp(power);
	}
}
