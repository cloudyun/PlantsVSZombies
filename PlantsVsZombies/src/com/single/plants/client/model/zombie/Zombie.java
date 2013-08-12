package com.single.plants.client.model.zombie;

import com.single.plants.client.model.PVZResources;
import com.single.plants.client.widget.Zombier;

public interface Zombie extends PVZResources {

	public String getId();
	public String getName();
	
	public Zombier createZombier();
}
