package com.single.plants.client.model.zombie;

import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.zombie.Zombier1Widget;


public class Zombie1 extends AbstractZombie {

	private static Zombie1 zombie1=new Zombie1();
	public static Zombie1 get(){
		return zombie1;
	}
	{
		id="Zombie1";
	}
	
	@Override
	public Zombier createZombier() {
		return new Zombier1Widget(this);
	}
}
