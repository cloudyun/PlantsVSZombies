package com.single.plants.client.model.weapon;

import com.google.gwt.resources.client.ImageResource;

public class PeashooterBullet extends Weapon {

	{
		id="PeashooterBullet";
	}
	
	@Override
	protected ImageResource getImageResource() {
		return resource.peashooterBullet();
	}


}
