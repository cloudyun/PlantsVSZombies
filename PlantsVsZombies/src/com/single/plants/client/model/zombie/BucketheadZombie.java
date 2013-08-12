package com.single.plants.client.model.zombie;

import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.zombie.BucketheadZombier;

public class BucketheadZombie extends AbstractZombie {

	private static BucketheadZombie zombie=new BucketheadZombie();
	public static BucketheadZombie get(){
		return zombie;
	}
	{
		id="BucketheadZombie";
		
	}
	@Override
	public Zombier createZombier() {
		return new BucketheadZombier(this);
	}

}
