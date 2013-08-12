package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.SnowPeaer;

/**
 * 寒冰射手
 */
public class SnowPea extends AbstractPlant {
	{
		id="SnowPea";
		name="寒冰射手";
		price=175;
		desc="寒冰射手可造成伤害，同时又有减速效果";
		freeze=7500;
	}
	
	public static SnowPea snowPea;
	public static SnowPea get(){
		if(snowPea==null){
			snowPea=new SnowPea();
		}
		return snowPea;
	}

	private SnowPea(){}
	
	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.snowPea());
	}

	@Override
	public Planter createPlanter() {
		return new SnowPeaer(this);
	}

}
