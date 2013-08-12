package com.single.plants.client.model.zombie;

import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.zombie.ConeheadZombier;


public class ConeheadZombie extends AbstractZombie {

	private static ConeheadZombie zombie=new ConeheadZombie();
	public static ConeheadZombie get(){
		return zombie;
	}
	{
		id="ConeheadZombie";
	}

	@Override
	public Zombier createZombier() {
		return new ConeheadZombier(this);
	}

}
