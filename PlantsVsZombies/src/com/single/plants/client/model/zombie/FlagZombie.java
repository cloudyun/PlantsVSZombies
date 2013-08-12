package com.single.plants.client.model.zombie;

import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.zombie.FlagZombier;


public class FlagZombie extends AbstractZombie {

	private static FlagZombie zombie=new FlagZombie();
	public static FlagZombie get(){
		return zombie;
	}
	{
		id="FlagZombie";
	}
	@Override
	public Zombier createZombier() {
		return new FlagZombier(this);
	}

}
