package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;


public class Jalapeno extends AbstractPlant {
	{
		id="Jalapeno";
		name="火爆辣椒";
		price=75;
		desc="";
		freeze=7500;
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.jalapeno());
	}

	@Override
	public Planter createPlanter() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
