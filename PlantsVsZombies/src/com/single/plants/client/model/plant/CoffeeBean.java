package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;


public class CoffeeBean extends AbstractPlant {
	{
		id="CoffeeBean";
		name="咖啡豆";
		price=75;
		desc="";
		freeze=7500;
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.coffeeBean());
	}

	@Override
	public Planter createPlanter() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
