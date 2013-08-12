package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;


public class LilyPad extends AbstractPlant {
	{
		id="LilyPad";
		name="花盆";
		price=150;
		desc="";
		freeze=50;
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.lilyPad());
	}

	@Override
	public Planter createPlanter() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
