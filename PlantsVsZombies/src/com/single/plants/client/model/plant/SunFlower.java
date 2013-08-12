package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.SunFlowerWidget;


public class SunFlower extends AbstractPlant {
	private static SunFlower sunFlower;
	public static SunFlower get(){
		if(sunFlower==null){
			sunFlower=new SunFlower();
		}
		return sunFlower;
	}
	{
		id="SunFlower";
		name="向日葵";
		desc="为你提供更多的阳光";
		price=50;
		freeze=5000;
		
	}
	
	private SunFlower(){}
	
	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.sunFlower());
	}

	@Override
	public Planter createPlanter() {
		return new SunFlowerWidget(this);
	}
	
}
