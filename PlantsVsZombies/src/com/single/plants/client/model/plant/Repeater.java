package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.Repeaterer;


public class Repeater extends AbstractPlant {
	private static Repeater repeater;
	public static Repeater get(){
		if(repeater==null){
			repeater=new Repeater();
		}
		return repeater;
	}
	{
		id="Repeater";
		name="双发射手";
		price=200;
		desc="";
		freeze=7500;
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.repeater());
	}
	
	@Override
	public Planter createPlanter() {
		return new Repeaterer(this);
	}

}
