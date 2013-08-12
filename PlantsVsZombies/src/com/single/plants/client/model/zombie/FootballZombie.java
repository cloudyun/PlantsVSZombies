package com.single.plants.client.model.zombie;

import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.zombie.FootballZomber;


public class FootballZombie extends AbstractZombie {

	private static FootballZombie zombie=new FootballZombie();
	public static FootballZombie get(){
		return zombie;
	}
	{
		id="FootballZombie";
	}

	@Override
	public Zombier createZombier() {
		return new FootballZomber(this);
	}

}
