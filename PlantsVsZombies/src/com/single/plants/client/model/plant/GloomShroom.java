package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;


public class GloomShroom extends AbstractPlant {
	{
		id="GloomShroom";
		name="曾哥";
		price=150;
		desc="";
		freeze=7500;
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.gloomShroom());
	}

	@Override
	public Planter createPlanter() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
