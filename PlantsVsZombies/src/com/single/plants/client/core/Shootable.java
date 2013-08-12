package com.single.plants.client.core;

import com.single.plants.client.model.weapon.Weapon;

public interface Shootable {

	
	Weapon getWeapon();
	
	boolean beforeShoot();
	
	void shoot();
}
