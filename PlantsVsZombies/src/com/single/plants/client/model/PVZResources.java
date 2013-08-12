package com.single.plants.client.model;

import com.google.gwt.core.client.GWT;
import com.single.plants.client.resources.plants.cards.PlantsCard;
import com.single.plants.client.resources.plants.gif.PlantsGif;
import com.single.plants.client.resources.scene.GameResource;
import com.single.plants.client.resources.template.TemplateResource;
import com.single.plants.client.resources.tool.ToolResource;
import com.single.plants.client.resources.weapon.WeaponResource;
import com.single.plants.client.resources.zombies.ZombiesResource;
import com.single.plants.client.stage.StageResource;

public interface PVZResources {
	

	PlantsCard plantsCard=GWT.create(PlantsCard.class);
	
	PlantsGif plantsGif=GWT.create(PlantsGif.class);
	
	GameResource gameResource=GWT.create(GameResource.class);
	
	ToolResource toolResource=GWT.create(ToolResource.class);
	
	WeaponResource weaponResource=GWT.create(WeaponResource.class);
	
	ZombiesResource zombiesResource=GWT.create(ZombiesResource.class);
	
	TemplateResource templateResource=GWT.create(TemplateResource.class);
	
	
	StageResource stageResource=GWT.create(StageResource.class);
}
