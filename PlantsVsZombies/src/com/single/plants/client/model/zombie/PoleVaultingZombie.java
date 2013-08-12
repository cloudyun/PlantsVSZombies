package com.single.plants.client.model.zombie;

import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.zombie.PoleVaultingZombier;

public class PoleVaultingZombie  extends AbstractZombie{

	private static PoleVaultingZombie zombie=new PoleVaultingZombie();
	public static PoleVaultingZombie get(){
		return zombie;
	}
	{
		id="PoleVaultingZombie";
	}
	@Override
	public Zombier createZombier() {
		return new PoleVaultingZombier(this);
	}

}
