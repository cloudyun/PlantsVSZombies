package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.Chomperer;


public class Chomper extends AbstractPlant {
	private static Chomper chomper;
	public static Chomper get(){
		if(chomper==null){
			chomper=new Chomper();
		}
		return chomper;
	}
	{
		id="Chomper";
		name="大嘴花";
		price=150;
		desc="";
		freeze=7500;
	}

	private Chomper(){}
	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.chomper());
	}
	
	@Override
	public Planter createPlanter() {
		return new Chomperer(this);
	}
}
