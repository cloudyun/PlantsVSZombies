package com.single.plants.client.model.zombie;

import java.util.HashMap;
import java.util.Map;

import com.framework.client.event.BaseObservable;

public abstract class AbstractZombie extends BaseObservable implements Zombie{

	public static Map<String, Zombie> zombiers=new HashMap<String, Zombie>();
	static{
		zombiers.put(BucketheadZombie.get().id, BucketheadZombie.get());
		zombiers.put(ConeheadZombie.get().id, ConeheadZombie.get());
		zombiers.put(FlagZombie.get().id, FlagZombie.get());
		zombiers.put(FootballZombie.get().id, FootballZombie.get());
		zombiers.put(PoleVaultingZombie.get().id, PoleVaultingZombie.get());
		zombiers.put(Zombie1.get().id, Zombie1.get());
	}
	
	protected String id;
	protected String name;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}


