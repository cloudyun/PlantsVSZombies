package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;


public class GatlingPea extends AbstractPlant {
	{
		id="GatlingPea";
		name="加特林";
		price=250;
		desc="";
		freeze=7500;
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.gatlingPea());
	}

	@Override
	public Planter createPlanter() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
