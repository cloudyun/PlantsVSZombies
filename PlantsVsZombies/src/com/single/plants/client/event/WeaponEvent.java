package com.single.plants.client.event;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventType;
import com.single.plants.client.model.weapon.Weapon;

public class WeaponEvent extends BaseEvent {

	private Weapon weapon;
	
	public WeaponEvent(EventType type,Weapon weapon) {
		super(type);
		this.weapon=weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
